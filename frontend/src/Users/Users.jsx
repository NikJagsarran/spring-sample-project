import React, { Component } from "react";

import UserTable from "./UserTable.jsx";
import NewUserForm from "./NewUserForm.jsx";
import { ToastContainer, toast } from "react-toastify";

import "react-toastify/dist/ReactToastify.css";

class Users extends Component {
  state = {
    usersObtained: false,
    errorMsg: "",
    usersUrl: "http://localhost:8080/api/users",
    hobbiesUrl: "http://localhost:8080/api/hobbies",
    users: [],
    hobbies: [],
    showEditModal: false
  };

  // Code is invoked after the component is mounted/inserted into the DOM tree.
  componentDidMount = () => {
    this.getHobbies();
    fetch(this.state.usersUrl, {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    })
      .then(result => result.json())
      .then(result => {
        console.log("Users fetched:");
        console.log(result);
        this.setState({
          usersObtained: true,
          users: result
        });
      })
      .catch(error => {
        console.error("Error:", error);
        this.setState({ errorMsg: error });
      });
  };

  removeUser = (index, id) => {
    const { users } = this.state;
    fetch(this.state.usersUrl + "/" + id, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json"
      }
    }).then(response => console.log("Response:", JSON.stringify(response)));

    this.setState({
      users: users.filter((user, i) => {
        return i !== index;
      })
    });
  };

  createUser = newUser => {
    newUser.email = newUser.email.toLowerCase();
    fetch(this.state.usersUrl, {
      method: "POST",
      body: JSON.stringify(newUser),
      headers: {
        "Content-Type": "application/json"
      }
    })
      .then(res => res.json())
      .then(response => {
        console.log("Response:", JSON.stringify(response));
        this.setState({ users: [...this.state.users, response] });
      })
      .catch(error => console.error("Error:", error));
  };

  updateUser = user => {
    //this.setState({ users: [...this.state.users] });
    console.log("updateUser(): " + JSON.stringify(user));
    fetch(this.state.usersUrl + "/" + user.id, {
      method: "PUT",
      body: JSON.stringify(user),
      headers: {
        "Content-Type": "application/json"
      }
    })
      .then(res => res.json())
      .then(response => console.log("Response:", JSON.stringify(response)))
      .catch(error => console.error("Error:", error));
  };

  getHobbies = () => {
    fetch(this.state.hobbiesUrl, {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    })
      .then(result => result.json())
      .then(result => {
        this.setState({ hobbies: result });
      })
      .catch(error => {
        console.error("Hobbies error:", error);
      });
  };

  render() {
    var result = <div className="contianer">Failed to obtain users</div>;
    if (this.state.usersObtained) {
      result = (
        <div className="contianer">
          <UserTable
            userData={this.state.users}
            removeUser={this.removeUser}
            handleSave={this.updateUser}
          />
          <NewUserForm
            handleSubmit={this.createUser}
            hobbies={this.state.hobbies}
          />
          <ToastContainer />
        </div>
      );
    }

    return result;
  }
}

export default Users;

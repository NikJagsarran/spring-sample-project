import React, { Component } from "react";
import EditUserModal from "./EditUser/EditUserModal.jsx";

class UserTable extends Component {
  constructor(props) {
    super(props);

    this.state = {
      editModalVisible: false,
      users: props.userData
    };
  }

  revealEditModal = user => {
    // Using setState()'s callback so we display the modal AFTER editModalUser has
    // been updated. Otherwise, the wrong (outdated) user may be sent to the modal.
    this.setState({ editModalUser: user }, () => {
      this.setState({ editModalVisible: true });
    });
  };

  hideEditModal = () => {
    this.setState({ editModalVisible: false });
  };

  render() {
    const { userData, handleSave, removeUser } = this.props;
    if (userData == null) {
      return (
        <div>
          <h2>Users</h2>
          <div>No users available</div>
        </div>
      );
    }

    return (
      <div>
        <table>
          <TableHeader />
          <TableBody
            userData={userData}
            removeUser={removeUser}
            editModalVisible={this.state.editModalVisible}
            revealEditModal={this.revealEditModal}
            hideEditModal={this.hideEditModal}
          />
        </table>
        <EditUserModal
          show={this.state.editModalVisible}
          handleClose={this.hideEditModal}
          handleSave={handleSave}
          user={this.state.editModalUser}
        />
      </div>
    );
  }
}

const TableHeader = () => {
  return (
    <thead>
      <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Favourite Hobby</th>
        <th>Hobby Category</th>
        <th>Edit</th>
        <th>Delete</th>
      </tr>
    </thead>
  );
};

const TableBody = props => {
  const rows = props.userData.map((row, index) => {
    return (
      <tr key={index}>
        <td>{row.name}</td>
        <td>{row.email}</td>
        <td>{row.hobbyName}</td>
        <td>{row.hobbyCat}</td>
        <td>
          <button
            className="btn btn-primary"
            onClick={() => props.revealEditModal(row)}
          >
            Edit...
          </button>
        </td>
        <td>
          <button
            className="btn btn-primary"
            onClick={() => props.removeUser(index, row.id)}
          >
            Delete
          </button>
        </td>
      </tr>
    );
  });
  return <tbody>{rows}</tbody>;
};

export default UserTable;

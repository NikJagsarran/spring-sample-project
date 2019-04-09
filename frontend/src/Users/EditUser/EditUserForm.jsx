import React, { Component } from "react";

class EditUserForm extends Component {
  constructor(props) {
    super(props);

    this.initialState = {
      id: props.user.id,
      name: props.user.name,
      email: props.user.email
    };

    this.state = this.initialState;
  }

  handleChange = event => {
    const { name, value } = event.target;
    this.setState({
      [name]: value
    });
  };

  saveUser = () => {
    console.log("saveUser: " + JSON.stringify(this.state));
    this.props.handleSave(this.state);
    // Confirmation toast here.
  };

  render() {
    const { name, email } = this.state;

    return (
      <form>
        <div className="form-group form-inline">
          <label>Name</label>
          <input
            type="text"
            name="name"
            value={name}
            onChange={this.handleChange}
          />
          <label>Email</label>
          <input
            type="text"
            name="email"
            value={email}
            onChange={this.handleChange}
          />
        </div>
        <input
          className="editUserSaveBtn"
          type="button"
          value="Save"
          onClick={this.saveUser}
        />
        <input type="button" value="Cancel" onClick={this.props.handleClose} />
      </form>
    );
  }
}

export default EditUserForm;

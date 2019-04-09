import React, { Component } from "react";
import { toast } from "react-toastify";

class NewUserForm extends Component {
  constructor(props) {
    super(props);

    this.initialState = {
      fields: [
        ["name", "Name", /^\w+$/, "Name must be at least one character"],
        [
          "email",
          "Email",
          /^[\w.+-]+@\w+(\.\w+)+$/,
          "Email is of an invalid format"
        ]
      ],
      hobby: "0",
      formValid: false
    };

    this.initialState.fields.forEach((row, index) => {
      this.initialState[row[0]] = {
        id: row[0],
        display: row[1],
        value: "",
        error: row[3],
        regex: row[2],
        hasChanged: false,
        valid: false
      };
    });

    this.state = this.initialState;
  }

  // Updates user fields in state when the text boxes are changed.
  handleChange = event => {
    const { name, value } = event.target;
    var fieldCopy = this.state[name];
    fieldCopy.value = value;
    fieldCopy.hasChanged = true;

    this.setState({ [name]: fieldCopy }, () => {
      this.validateField(name, value);
    });
  };

  // Tests a field against its validation regex to check if it's valid, and
  // sets the object's valid value accordingly. Also calls validateForm().
  validateField(fieldName, value) {
    var regex = this.state[fieldName].regex;
    var valid = regex.test(value);

    var fieldCopy = this.state[fieldName];
    fieldCopy.valid = valid;

    this.setState({ [fieldName]: fieldCopy }, this.validateForm);
  }

  // Checks if the whole form is valid by checking each field's valid value.
  validateForm() {
    var formValid = true;
    this.state.fields.forEach((row, index) => {
      formValid = this.state[row[0]].valid && formValid;
    });

    this.setState({
      formValid: formValid
    });
  }

  // Sets Bootstrap's 'is-(in)?valid' className.
  errorClass(fieldName) {
    if (!this.state[fieldName].hasChanged) {
      return "";
    }
    return this.state[fieldName].valid ? "is-valid" : "is-invalid";
  }

  submitNewUser = () => {
    // Convert the necessary fields to clean JSON.
    var fieldVals = {};
    this.state.fields.forEach(row => {
      fieldVals[row[0]] = this.state[row[0]].value;
    });
    fieldVals.hobby = this.state.hobby;

    this.props.handleSubmit(fieldVals);
    this.setState(this.initialState);
    toast.success("User added!!", {
      position: "bottom-center",
      autoClose: 10000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true
    });
  };

  render() {
    // Generate text boxes for fields that require it.
    const formFields = this.state.fields.map((row, index) => {
      const field = this.state[row[0]];
      return (
        <div key={index} className="form-group">
          <label>{field.display}</label>
          <input
            type="text"
            name={field.id}
            // This syntax is important for inline function calls in a string.
            className={`form-control ${this.errorClass(field.id)}`}
            value={this.state[field.id].value}
            onChange={this.handleChange}
          />
          <div hidden={!field.hasChanged || field.valid}>{field.error}</div>
        </div>
      );
    });

    // Hobbies into HTML <options> for the dropdown.
    const hobbiesAsOptions = this.props.hobbies.map((row, index) => {
      return (
        <option key={index} value={row.id}>
          {row.name}
        </option>
      );
    });

    return (
      <div>
        <h2>Add New User</h2>
        <form>{formFields}</form>
        <div className="form-group">
          <label>Favourite Hobby</label>
          <select
            className="form-control"
            onChange={event => {
              this.setState({ hobby: event.target.value });
            }}
          >
            {hobbiesAsOptions}
          </select>
        </div>
        <input
          type="button"
          value="Submit"
          disabled={!this.state.formValid}
          onClick={this.submitNewUser}
        />
      </div>
    );
  }
}

export default NewUserForm;

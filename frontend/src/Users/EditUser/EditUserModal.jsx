import React from "react";
import "./Modal.css";
import EditUserForm from "./EditUserForm.jsx";

const EditUserModal = ({ show, user, handleClose, handleSave }) => {
  if (!show) {
    return <div className="modal display-none" />;
  }

  // Calls handleClose() if escape (keyCode 27) is pressed.
  document.addEventListener(
    "keydown",
    event => {
      if (event.keyCode === 27) {
        handleClose();
      }
    },
    false
  );

  return (
    <div className="modal display-block">
      <section className="modal-main">
        <h2>Edit User</h2>
        <EditUserForm
          user={user}
          handleSave={handleSave}
          handleClose={handleClose}
        />
      </section>
    </div>
  );
};

export default EditUserModal;

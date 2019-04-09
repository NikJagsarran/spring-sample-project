import React, { Component } from "react";
import Users from "./Users/Users";

class App extends Component {
  render() {
    return (
      <div className="container">
        <h2>User Database</h2>
        <Users />
      </div>
    );
  }
}

export default App;

import React, { Component } from "react";
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import "./Login.css";

export default class Login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      name: "",
      email: "",
      password: ""
    };
  }

  validateLoginForm() {
    // regex from http://stackoverflow.com/questions/46155/validate-email-address-in-javascript
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    return re.test(this.state.email) && this.state.password.length > 7;
  }

  validateRegisterForm() {
    // regex from http://stackoverflow.com/questions/46155/validate-email-address-in-javascript
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    return this.state.name.length > 0 && re.test(this.state.email) && this.state.password.length > 7;
  }

  handleChange = event => {
    this.setState({
      [event.target.id]: event.target.value
    });
  }

  handleSubmitLogin = event => {
    event.preventDefault();

    try {
      var xhr = new XMLHttpRequest();
      var url =  Constants.API + "/login";
      xhr.open("POST", url, true);
      xhr.setRequestHeader("Content-Type", "application/json");
      xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
          var json = JSON.parse(xhr.responseText);
          console.log(json.email + ", " + json.password);
        }
      };
      var data = JSON.stringify({"email": this.state.email, "password": this.state.password});
      xhr.send(data);
    }
    catch (e) {
      // Exception handling
    }
  }

  handleSubmitRegister = event => {
    event.preventDefault();

    try {
      var xhr = new XMLHttpRequest();
      var url =  Constants.API + "/register";
      xhr.open("POST", url, true);
      xhr.setRequestHeader("Content-Type", "application/json");
      xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
          var json = JSON.parse(xhr.responseText);
          console.log(json.name + "," + json.email + ", " + json.password);
        }
      };
      var data = JSON.stringify({"name": this.state.name, "email": this.state.email, "password": this.state.password});
      xhr.send(data);
    }
    catch (e) {
      // Exception handling
    }
  }

  render() {
    return (
      <SplitterLayout>
        <div className="Login-Register">
            <p className="Title"> Login </p>

            <form name="Login" onSubmit={this.handleSubmitLogin}>
              <FormGroup controlId="email" bsSize="large">
                <ControlLabel>Email</ControlLabel>
                <FormControl
                  autoFocus
                  type="email"
                  value={this.state.email}
                  onChange={this.handleChange}
                  />
              </FormGroup>
              <FormGroup controlId="password" bsSize="large">
                <ControlLabel>Password</ControlLabel>
                <FormControl
                  value={this.state.password}
                  onChange={this.handleChange}
                  type="password"
                  />
              </FormGroup>
              <Button
                block
                bsSize="large"
                disabled={!this.validateLoginForm()}
                type="submit"
              >
                Login
            </Button>
          </form>

            <p className="Title"> Register </p>

            <form name="Register" onSubmit={this.handleSubmitRegister}>
              <FormGroup controlId="name" bsSize="large">
                <ControlLabel>Name</ControlLabel>
                <FormControl
                  autoFocus
                  type="name"
                  value={this.state.name}
                  onChange={this.handleChange}
                />
                </FormGroup>
                <FormGroup controlId="email" bsSize="large">
                  <ControlLabel>Email</ControlLabel>
                  <FormControl
                  autoFocus
                  type="email"
                  value={this.state.email}
                  onChange={this.handleChange}
                />
                </FormGroup>
                <FormGroup controlId="password" bsSize="large">
                  <ControlLabel>Password</ControlLabel>
                  <FormControl
                    value={this.state.password}
                    onChange={this.handleChange}
                    type="password"
                  />
                  </FormGroup>
                <Button
                  block
                  bsSize="large"
                  disabled={!this.validateRegisterForm()}
                  type="submit"
                >
                  Register
                </Button>
          </form>
        </div>
    );
  }
}

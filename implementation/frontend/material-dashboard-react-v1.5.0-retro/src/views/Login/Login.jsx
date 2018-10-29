import React, { Component } from "react";
import "../../assets/css/material-dashboard-react.css";

// core components/views
import GridItem from "components/Grid/GridItem.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import CustomInput from "components/CustomInput/CustomInput.jsx";
import Card from "components/Card/Card.jsx";
import CardHeader from "components/Card/CardHeader.jsx";
import CardBody from "components/Card/CardBody.jsx";
import CardFooter from "components/Card/CardFooter.jsx";
import Button from "components/CustomButtons/Button.jsx";

import LoginRepository from '../../api_calls/login.js';

export default class Login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      nickname: "",
      email: "",
      firstName: "",
      lastName: "",
      password: ""
    };
    //this.handleChange = this.handleChange.bind(this);
  }

  validateLoginForm() {
    // regex from http://stackoverflow.com/questions/46155/validate-email-address-in-javascript
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(this.state.email) && this.state.password.length > 7;
  }

  validateRegisterForm() {
    // regex from http://stackoverflow.com/questions/46155/validate-email-address-in-javascript
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return this.state.nickname.length > 0 && re.test(this.state.email) && this.state.password.length > 7;
  }

  handleChange = event => {
    this.setState({
      [event.target.id]: event.target.value
    });
  }

  handleSubmitLogin = event => {
    event.preventDefault();
    LoginRepository.login(this.state.loginNickname, this.state.loginEmail, this.state.loginPassword)
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });
  }

  handleSubmitForget = event => {
    event.preventDefault();

    LoginRepository.forget(this.state.nickname, this.state.email, this.state.password)
      .then(res => {
        console.log(res);

      })
      .catch(err => {
        console.log(err);
      });
  }

  handleSubmitRegister = event => {
    event.preventDefault();

    LoginRepository.register(this.state.registerNickname, this.state.registerFirstName, this.state.registerLastName, this.state.registerEmail, this.state.registerPassword)
      .then(res => {
        console.log(res.headers);
      })
      .catch(err => {
        console.log(err);
      });
  }

  render() {
    return (
      <div>
        <GridContainer>
          <GridItem xs={10} sm={10} md={5}>
            <Card>
              <CardHeader color="primary">
                <h4 className="titleWhite">Login</h4>
              </CardHeader>
              <CardBody>
                <GridContainer>
                  <GridItem xs={10} sm={10} md={8}>
                    <CustomInput
                      labelText="Nickname"
                      id="loginNickname"
                      value={this.state.nickname}
                      inputProps={{
                        type:"text",
                        onChange: this.handleChange
                      }}
                      formControlProps={{
                        fullWidth: true,
                        required: false
                      }}
                    />
                  </GridItem>
                </GridContainer>
                <GridContainer>
                  <GridItem xs={10} sm={10} md={8}>
                    <CustomInput
                      labelText="Email address"
                      id="loginEmail"
                      value={this.state.email}
                      inputProps={{
                        type:"email",
                        onChange: this.handleChange
                      }}
                      formControlProps={{
                        fullWidth: true
                      }}
                    />
                  </GridItem>
                </GridContainer>
                <GridContainer>
                  <GridItem xs={10} sm={10} md={8}>
                    <CustomInput
                      labelText="Password"
                      id="loginPassword"
                      value={this.state.password}
                      inputProps={{
                        type:"password",
                        onChange: this.handleChange
                      }}
                      formControlProps={{
                        fullWidth: true,
                        required: true
                      }}
                    />
                  </GridItem>
                </GridContainer>
              </CardBody>
              <CardFooter>
                <Button color="primary" onClick={this.handleSubmitLogin}>Login</Button>
                <Button color="transparent" onClick={this.handleSubmitForget}>Forget password</Button>
              </CardFooter>
            </Card>
          </GridItem>


          <GridItem xs={10} sm={10} md={5}>
            <Card>
              <CardHeader color="primary">
                <h4 className="titleWhite">Register</h4>
              </CardHeader>
              <CardBody>
                <GridContainer>
                  <GridItem xs={10} sm={10} md={8}>
                    <CustomInput
                      labelText="Nickname"
                      id="registerNickname"
                      value={this.state.nickname}
                      inputProps={{
                        type:"text",
                        onChange: this.handleChange
                      }}
                      formControlProps={{
                        fullWidth: true,
                        required: true
                      }}
                    />
                  </GridItem>
                </GridContainer>
                <GridContainer>
                  <GridItem xs={10} sm={10} md={8}>
                    <CustomInput
                      labelText="First Name"
                      id="registerFirstName"
                      value={this.state.firstName}
                      inputProps={{
                        type:"text",
                        onChange: this.handleChange
                      }}
                      formControlProps={{
                        fullWidth: true,
                        required: true
                      }}
                    />
                  </GridItem>
                </GridContainer>
                <GridContainer>
                  <GridItem xs={10} sm={10} md={8}>
                    <CustomInput
                      labelText="Last Name"
                      id="registerLastName"
                      value={this.state.lastName}
                      inputProps={{
                        type:"text",
                        onChange: this.handleChange
                      }}
                      formControlProps={{
                        fullWidth: true,
                        required: true
                      }}
                    />
                  </GridItem>
                </GridContainer>
                <GridContainer>
                  <GridItem xs={10} sm={10} md={8}>
                    <CustomInput
                      labelText="Email address"
                      id="registerEmail"
                      value={this.state.email}
                      inputProps={{
                        type:"email",
                        onChange: this.handleChange
                      }}
                      formControlProps={{
                        fullWidth: true,
                        required: true
                      }}
                    />
                  </GridItem>
                </GridContainer>
                <GridContainer>
                  <GridItem xs={10} sm={10} md={8}>
                    <CustomInput
                      labelText="Password"
                      id="registerPassword"
                      value={this.state.password}
                      inputProps={{
                        type:"password",
                        onChange: this.handleChange
                      }}
                      formControlProps={{
                        fullWidth: true,
                        required: true
                      }}
                    />
                  </GridItem>
                </GridContainer>
              </CardBody>
              <CardFooter>
                <Button color="primary" onClick={this.handleSubmitRegister}>Register</Button>
              </CardFooter>
            </Card>
          </GridItem>
        </GridContainer>
      </div>
    );
  }
}

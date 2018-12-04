import React, { Component } from "react";
import "../../assets/css/material-dashboard-react.css";
import axios from "axios";
import Cookies from "js-cookie";

import { HelpBlock } from 'react-bootstrap';
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
import FormValidator from 'components/FormValidator/FormValidator.js';

export default class Login extends Component {
  constructor(props) {
    super(props);

    this.loginSubmitted = false;
    this.registerSubmitted = false;

    this.handleChange = this.handleChange.bind(this);

    this.loginValidator = new FormValidator([
      {
        field: 'loginPassword',
        method: 'isEmpty',
        validWhen: false,
        message: 'Password is required.'
      }
    ]);

    this.registerValidator = new FormValidator([
      {
        field: 'registerNickname',
        method: 'isEmpty',
        validWhen: false,
        message: 'A nickname is required.'
      },
      {
        field: 'registerFirstName',
        method: 'isEmpty',
        validWhen: false,
        message: 'First name is required.'
      },
      {
        field: 'registerLastName',
        method: 'isEmpty',
        validWhen: false,
        message: 'Last name is required.'
      },
      {
        field: 'registerEmail',
        method: 'isEmpty',
        validWhen: false,
        message: 'Email is required.'
      },
      {
        field: 'registerEmail',
        method: 'isEmail',
        validWhen: true,
        message: 'Please provide a valid loginEmail.'
      },
      {
        field: 'registerPassword',
        method: 'isEmpty',
        validWhen: false,
        message: 'Password is required.'
      },
      {
        field: 'registerPassword',
        method: this.passwordLength,
        validWhen: true,
        message: 'Password must be at least 8 characters long, this is for your safety.'
      },
      {
        field: 'registerPassword',
        method: this.passwordFormat,
        validWhen: true,
        message: 'Password needs to have at least one of: a small-case letter, an upper-case letter and a number. Please do not try to use characters that are too weird.'
      },
      {
        field: 'registerPasswordConfirmation',
        method: 'isEmpty',
        validWhen: false,
        message: 'Password confirmation is required.'
      },
      {
        field: 'registerPasswordConfirmation',
        method: this.passwordMatch,   // notice that we are passing a custom function here
        validWhen: true,
        message: 'Password and password confirmation do not match. Please check again.'
      }
    ]);

    this.state = {
      nickname: "",
      loginEmail: "",
      loginPassword: "",
      registerNickname: "",
      registerEmail: "",
      registerFirstName: "",
      registerLastName: "",
      registerPassword: "",
      registerPasswordConfirmation: "",
      loginValidation: this.loginValidator.valid(),
      registerValidation: this.registerValidator.valid(),
    }

  }

  // It makes the most sense to perform the password checks below only during register period.

  // regex from http://stackoverflow.com/questions/46155/validate-email-address-in-javascript
  //var emailRegex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

  //regex from https://stackoverflow.com/questions/14850553/javascript-regex-for-password-containing-at-least-8-characters-1-number-1-uppe
  //var passwordRegexWithSpecialCharacters = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,50}$/

  //regex from https://stackoverflow.com/questions/14850553/javascript-regex-for-password-containing-at-least-8-characters-1-number-1-uppe
  //var passwordRegexWithNoSpecialCharacters = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,50}$/
validatePasswordFormat() {
  // regex does not allow special characters for now, look above for details.
  // Note: Regex needs to have / at the beginning and end.
  var re = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,50}$/
  return re.test(this.state.registerPassword);
}
  passwordFormat = this.validatePasswordFormat.bind(this)
  passwordLength = (password, state) => (state.registerPassword.length) >= 7
  passwordMatch = (confirmation, state) => (state.registerPassword === confirmation)

// Functions are below.

  handleChange = event => {
    event.preventDefault();

    this.setState({
      [event.target.name]: event.target.value,
    });
  }

  handleSubmitLogin = event => {
    event.preventDefault();

    const validation = this.loginValidator.validate(this.state);
    this.setState({ validation });
    this.loginSubmitted = true;

    if (validation.isValid) {
      LoginRepository.login(this.state.loginNickname, this.state.loginEmail, this.state.loginPassword)
        .then(res => {
          console.log(res);
          window.location.reload();
        })
        .catch(err => {
          console.log(err);
        });
    }
  }

// TODO implement once backend is ready.
  handleSubmitForget = event => {
    event.preventDefault();

    LoginRepository.forget(this.state.loginNickname, this.state.loginEmail, this.state.loginPassword)
      .then(res => {
        console.log(res);

      })
      .catch(err => {
        console.log(err);
      });
  }

  handleSubmitRegister = event => {
    event.preventDefault();

    const validation = this.registerValidator.validate(this.state);
    this.setState({ validation });
    this.registerSubmitted = true;

    if (validation.isValid) {
      LoginRepository.register(this.state.registerNickname, this.state.registerFirstName, this.state.registerLastName, this.state.registerEmail, this.state.registerPassword)
        .then(res => {
          console.log(res);
          window.location.reload();
        })
        .catch(err => {
          console.log(err);
        });
    }
  }

  render() {
    let loginValidation = this.loginSubmitted ?                         // if the form has been submitted at least once
                      this.loginValidator.validate(this.state) :   // then check validity every time we render
                      this.state.loginValidation                   // otherwise just use what's in state

    let registerValidation = this.registerSubmitted ?
                      this.registerValidator.validate(this.state) :
                      this.state.registerValidation

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
                      value={this.state.loginNickname}
                      inputProps={{
                        name:"loginNickname",
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
                      value={this.state.loginEmail}
                      inputProps={{
                        name:"loginEmail",
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
                      className={loginValidation.loginPassword.isInvalid && 'has-error'}
                      labelText="Password"
                      id="loginPassword"
                      value={this.state.loginPassword}
                      inputProps={{
                        name:"loginPassword",
                        type:"password",
                        onChange: this.handleChange
                      }}
                      formControlProps={{
                        fullWidth: true,
                        required: true
                      }}
                    />
                    <HelpBlock>{loginValidation.loginPassword.message}</HelpBlock>
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
                      className={registerValidation.registerNickname.isInvalid && 'has-error'}
                      labelText="Nickname"
                      id="registerNickname"
                      value={this.state.registerNickname}
                      inputProps={{
                        name:"registerNickname",
                        type:"text",
                        onChange: this.handleChange
                      }}
                      formControlProps={{
                        fullWidth: true,
                        required: true
                      }}
                    />
                    <HelpBlock>{registerValidation.registerNickname.message}</HelpBlock>
                  </GridItem>
                </GridContainer>
                <GridContainer>
                  <GridItem xs={10} sm={10} md={8}>
                    <CustomInput
                      className={registerValidation.registerFirstName.isInvalid && 'has-error'}
                      labelText="First Name"
                      id="registerFirstName"
                      value={this.state.registerFirstName}
                      inputProps={{
                        name:"registerFirstName",
                        type:"text",
                        onChange: this.handleChange
                      }}
                      formControlProps={{
                        fullWidth: true,
                        required: true
                      }}
                    />
                    <HelpBlock>{registerValidation.registerFirstName.message}</HelpBlock>
                  </GridItem>
                </GridContainer>
                <GridContainer>
                  <GridItem xs={10} sm={10} md={8}>
                    <CustomInput
                      className={registerValidation.registerLastName.isInvalid && 'has-error'}
                      labelText="Last Name"
                      id="registerLastName"
                      value={this.state.registerLastName}
                      inputProps={{
                        name:"registerLastName",
                        type:"text",
                        onChange: this.handleChange
                      }}
                      formControlProps={{
                        fullWidth: true,
                        required: true
                      }}
                    />
                    <HelpBlock>{registerValidation.registerLastName.message}</HelpBlock>
                  </GridItem>
                </GridContainer>
                <GridContainer>
                  <GridItem xs={10} sm={10} md={8}>
                    <CustomInput
                      className={registerValidation.registerEmail.isInvalid && 'has-error'}
                      labelText="Email address"
                      id="registerEmail"
                      value={this.state.registerEmail}
                      inputProps={{
                        name:"registerEmail",
                        type:"email",
                        onChange: this.handleChange
                      }}
                      formControlProps={{
                        fullWidth: true,
                        required: true
                      }}
                    />
                    <HelpBlock>{registerValidation.registerEmail.message}</HelpBlock>
                  </GridItem>
                </GridContainer>
                <GridContainer>
                  <GridItem xs={10} sm={10} md={8}>
                    <CustomInput
                      className={registerValidation.registerPassword.isInvalid && 'has-error'}
                      labelText="Password"
                      id="registerPassword"
                      value={this.state.registerPassword}
                      inputProps={{
                        name:"registerPassword",
                        type:"password",
                        onChange: this.handleChange
                      }}
                      formControlProps={{
                        fullWidth: true,
                        required: true
                      }}
                    />
                    <HelpBlock>{registerValidation.registerPassword.message}</HelpBlock>
                  </GridItem>
                </GridContainer>
                <GridContainer>
                  <GridItem xs={10} sm={10} md={8}>
                    <CustomInput
                      className={registerValidation.registerPasswordConfirmation.isInvalid && 'has-error'}
                      labelText="Password Confirmation"
                      id="registerPasswordConfirmation"
                      value={this.state.registerPasswordConfirmation}
                      inputProps={{
                        name:"registerPasswordConfirmation",
                        type:"password",
                        onChange: this.handleChange
                      }}
                      formControlProps={{
                        fullWidth: true,
                        required: true
                      }}
                    />
                    <HelpBlock>{registerValidation.registerPasswordConfirmation.message}</HelpBlock>
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

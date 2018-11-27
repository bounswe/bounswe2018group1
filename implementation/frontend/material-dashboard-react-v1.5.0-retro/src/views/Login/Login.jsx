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
import FormValidator from 'components/FormValidator/FormValidator.js';
import { HelpBlock } from 'react-bootstrap';

export default class Login extends Component {
  constructor(props) {
    super(props);

    this.submitted = false;

    //this.handleChange = this.handleChange.bind(this);

    this.validator = new FormValidator([
      {
        field: 'loginNickname',
        method: 'isEmpty',
        validWhen: false,
        message: 'A nickname is required.'
      },
/*      {
        field: 'firstName',
        method: 'isEmpty',
        validWhen: false,
        message: 'First name is required.'
      },
      {
        field: 'lastName',
        method: 'isEmpty',
        validWhen: false,
        message: 'Last name is required.'
      }, */
      {
        field: 'loginEmail',
        method: 'isEmpty',
        validWhen: false,
        message: 'Email is required.'
      },
      {
        field: 'loginEmail',
        method: 'isEmail',
        validWhen: true,
        message: 'Please provide a valid email.'
      },
      {
        field: 'loginPassword',
        method: 'isEmpty',
        validWhen: false,
        message: 'Password is required.'
      },
      {
        field: 'loginPassword',
        method: this.passwordLength,
        validWhen: true,
        message: 'Password must be at least 8 characters long, this is for your safety.'
      },
      {
        field: 'loginPassword',
        method: this.passwordFormat,
        validWhen: true,
        message: 'Password needs to have at least one of: a small-case letter, an upper-case letter and a number. Please do not try to use characters that are too weird.'
      },
  /*    {
        field: 'password_confirmation',
        method: 'isEmpty',
        validWhen: false,
        message: 'Password confirmation is required.'
      },
      {
        field: 'password_confirmation',
        method: this.passwordMatch,   // notice that we are passing a custom function here
        validWhen: true,
        message: 'Password and password confirmation do not match. Please check again.'
      }   */
    ]);

    this.state = {
      loginNickname: "",
      loginEmail: "",
      firstName: "",
      lastName: "",
      loginPassword: "",
      password_confirmation: "",
      validation: this.validator.valid(),
    }

  }

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
  return re.test(this.state.password);
}

  passwordFormat = this.validatePasswordFormat.bind(this)
  passwordLength = (password, state) => (state.loginPassword.length) >= 7
  passwordMatch = (confirmation, state) => (state.password === confirmation)

  handleChange = event => {
    event.preventDefault();

    this.setState({
      [event.target.id]: event.target.value,
    });
  }

  handleSubmitLogin = event => {
    event.preventDefault();

    const validation = this.validator.validate(this.state);
    this.setState({ validation });
    this.submitted = true;

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

    const validation = this.validator.validate(this.state);
    this.setState({ validation });
    this.submitted = true;

    if (validation.isValid) {
      LoginRepository.register(this.state.registerNickname, this.state.registerFirstName, this.state.registerLastName, this.state.registerEmail, this.state.registerPassword)
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
    }
  }

  render() {
    let validation = this.submitted ?                         // if the form has been submitted at least once
                      this.validator.validate(this.state) :   // then check validity every time we render
                      this.state.validation                   // otherwise just use what's in state

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
                      className={validation.loginEmail.isInvalid && 'has-error'}
                      labelText="Email address"
                      id="loginEmail"
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
                    <HelpBlock>{validation.loginEmail.message}</HelpBlock>
                  </GridItem>
                </GridContainer>
                <GridContainer>
                  <GridItem xs={10} sm={10} md={8}>
                    <CustomInput
                      className={validation.loginPassword.isInvalid && 'has-error'}
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
                    <HelpBlock>{validation.loginPassword.message}</HelpBlock>
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
                      //className={validation.nickname.isInvalid && 'has-error'}
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
                      //className={validation.firstName.isInvalid && 'has-error'}
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
                      //className={validation.lastName.isInvalid && 'has-error'}
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
                      //className={validation.email.isInvalid && 'has-error'}
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
                      //className={validation.password.isInvalid && 'has-error'}
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
                <GridContainer>
                  <GridItem xs={10} sm={10} md={8}>
                    <CustomInput
                      //className={validation.password_confirmation.isInvalid && 'has-error'}
                      labelText="Password Confirmation"
                      id="registerPasswordConfirmation"
                      value={this.state.password_confirmation}
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

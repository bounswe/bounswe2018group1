import React, { Component } from "react";
import { Button } from "react-bootstrap";
import "../../assets/css/material-dashboard-react.css";

// @material-ui/core
import withStyles from "@material-ui/core/styles/withStyles";

// @material-ui/icons
import Dashboard from "@material-ui/icons/Dashboard";

// core components/views
import GridItem from "components/Grid/GridItem.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import CustomInput from "components/CustomInput/CustomInput.jsx";
import Danger from "components/Typography/Danger.jsx";
import Card from "components/Card/Card.jsx";
import CardHeader from "components/Card/CardHeader.jsx";
import CardIcon from "components/Card/CardIcon.jsx";
import CardBody from "components/Card/CardBody.jsx";
import CardFooter from "components/Card/CardFooter.jsx";

import LoginRepository from '../../api_calls/login.js';

export default class Login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      nickname: "",
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
    return this.state.nickname.length > 0 && re.test(this.state.email) && this.state.password.length > 7;
  }

  handleChange = event => {
    this.setState({
      [event.target.id]: event.target.value
    });
  }

  handleSubmitLogin = event => {
    event.preventDefault();

    console.log(LoginRepository);

    LoginRepository.login(this.state.nickname, this.state.email, this.state.password)
    .then(res => {
      console.log(res.headers);
    })
    .catch(err => {
      console.log(err);
    });
  }

  handleSubmitRegister = event => {
    event.preventDefault();

    console.log(LoginRepository);

    LoginRepository.register(this.state.nickname, this.state.email, this.state.password)
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
                      id="nickname"
                      value={this.state.nickname}
                      onChange={this.handleChange}
                      inputProps={{ type:"text" }}
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
                      id="email"
                      value={this.state.email}
                      onChange={this.handleChange}
                      inputProps={{ type:"email" }}
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
                      id="password"
                      value={this.state.password}
                      onChange={this.handleChange}
                      inputProps={{ type:"password" }}
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
                      id="nickname"
                      value={this.state.nickname}
                      onChange={this.handleChange}
                      inputProps={{ type:"text" }}
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
                      id="email"
                      value={this.state.email}
                      onChange={this.handleChange}
                      inputProps={{ type:"email" }}
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
                      id="password"
                      value={this.state.password}
                      onChange={this.handleChange}
                      inputProps={{ type:"password" }}
                      formControlProps={{
                        fullWidth: true,
                        required: true
                      }}
                    />
                  </GridItem>
                </GridContainer>
              </CardBody>
              <CardFooter>
                <Button color="primary">Register</Button>
              </CardFooter>
            </Card>
          </GridItem>
        </GridContainer>
      </div>
    );
  }
}

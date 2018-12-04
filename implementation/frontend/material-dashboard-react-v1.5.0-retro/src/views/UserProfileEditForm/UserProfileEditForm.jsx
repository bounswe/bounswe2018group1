import React from "react";
import PropTypes from "prop-types";
// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
// import InputLabel from "@material-ui/core/InputLabel";
// core components
import {Select, MenuItem, FormControl, InputLabel, FilledInput} from '@material-ui/core';
import GridItem from "components/Grid/GridItem.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import CustomInput from "components/CustomInput/CustomInput.jsx";
import DateInput from "components/DateInput/DateInput.jsx";
import CountrySelect from "components/CountrySelect/CountrySelect.jsx";
import Button from "components/CustomButtons/Button.jsx";
import Card from "components/Card/Card.jsx";
import CardHeader from "components/Card/CardHeader.jsx";
import CardAvatar from "components/Card/CardAvatar.jsx";
import CardBody from "components/Card/CardBody.jsx";
import CardFooter from "components/Card/CardFooter.jsx";

import UserRepository from '../../api_calls/user.js';

import { CountryDropdown, RegionDropdown, CountryRegionData } from 'react-country-region-selector';

import avatar from "assets/img/faces/girl.jpg";

const styles = {
  cardCategoryWhite: {
    color: "rgba(255,255,255,.62)",
    margin: "0",
    fontSize: "14px",
    marginTop: "0",
    marginBottom: "0"
  },
  cardTitleWhite: {
    color: "#FFFFFF",
    marginTop: "0px",
    minHeight: "auto",
    fontWeight: "300",
    fontFamily: "'Roboto', 'Helvetica', 'Arial', sans-serif",
    marginBottom: "3px",
    textDecoration: "none"
  }
};

class UserProfileEditForm extends React.Component {
  constructor(props) {
      super(props);
      // this.state = {
      //   updatedData: {
      //     user: this.props.user
      //   }
      // };
      // this.state = {value: ''};
      // this.state = { country: '', region: '' };
      this.state = {
        user: props.user,
        open: false
      };

      this.handleChange = this.handleChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
      event.preventDefault();
      this.props.onChangeField(event.target.name, event.target.value);
    }

    handleClose = () => {
      this.setState({ open: false });
    };

    handleOpen = () => {
      this.setState({ open: true });
    };

    handleSubmit(event) {
      event.preventDefault();
      this.props.onUpdatePressed();
    }

    render() {
      const { classes, country, region, user } = this.props;

      if (!user.id) {
        return null;
      }

      return (
          <GridContainer style={{ width: "100%" }}>
            <GridItem xs={12} sm={12} md={12}>
              <Card>
                <CardHeader color="primary">
                  <h4 className={classes.cardTitleWhite}>Edit Profile</h4>
                  <p className={classes.cardCategoryWhite}>Complete your profile</p>
                </CardHeader>
                <CardBody>
                  <GridContainer>
                    <GridItem xs={12} sm={12} md={4}>
                      <CustomInput
                        labelText="Username"
                        id="nickname"
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          name: 'nickname',
                          defaultValue: user.nickname,
                          onChange: this.handleChange
                        }}
                      />
                    </GridItem>
                    <GridItem xs={12} sm={12} md={4}>
                      <CustomInput
                        labelText="First Name"
                        id="first-name"
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          name: 'firstName',
                          onChange: this.handleChange,
                          defaultValue: user.firstName
                        }}
                      />
                    </GridItem>
                    <GridItem xs={12} sm={12} md={4}>
                      <CustomInput
                        labelText="Last Name"
                        id="last-name"
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          name: 'lastName',
                          onChange: this.handleChange,
                          defaultValue: user.lastName
                        }}
                      />
                    </GridItem>
                  </GridContainer>

                  <GridContainer>
                    <GridItem xs={12} sm={12} md={3}>
                      <FormControl fullWidth={true}>
                        <InputLabel>Gender</InputLabel>
                        <Select
                          open={this.state.open}
                          onClose={this.handleClose}
                          onOpen={this.handleOpen}
                          id="gender"
                          name="gender"
                          onChange={this.handleChange}
                          value={this.props.user.gender}
                        >
                          <MenuItem value="FEMALE">Female</MenuItem>
                          <MenuItem value="MALE">Male</MenuItem>
                          <MenuItem value="NOT_TO_DISCLOSE">Others</MenuItem>
                        </Select>
                    </FormControl>
                    </GridItem>

                    <GridItem xs={12} sm={12} md={3}>
                      <FormControl fullWidth={true}>

                        <InputLabel>Birthday</InputLabel>
                        <DateInput
                          id="birthday"
                          className="textField"
                          formControlProps={{
                            fullWidth: true
                          }}
                          inputProps={{
                            name: 'birthday',
                            onChange: this.handleChange,
                            defaultValue: user.birthday
                          }}
                        />
                      </FormControl>
                    </GridItem>
                    <GridItem xs={12} sm={12} md={3}>
                      <CountrySelect
                        id="country"
                        formControlProps={{
                          fullWidth: true
                        }}
                      />
                    </GridItem>
                  </GridContainer>

                  <GridContainer>
                    <GridItem xs={12} sm={12} md={12}>
                      <CustomInput
                        labelText="Personel info"
                        id="about-me"
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          name: 'bio',
                          onChange: this.handleChange,
                          multiline: true,
                          rows: 5,
                          defaultValue: user.bio
                        }}
                      />
                    </GridItem>
                  </GridContainer>
                </CardBody>
                <CardFooter>
                  <Button color="primary" onClick={this.handleSubmit}>Update Profile</Button>
                </CardFooter>
              </Card>
            </GridItem>
          </GridContainer>
      );
    }
}

UserProfileEditForm.propTypes = {
  user: PropTypes.object.isRequired
};

export default withStyles(styles)(UserProfileEditForm);

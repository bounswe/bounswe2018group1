import React from "react";
import PropTypes from "prop-types";

// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
import InputLabel from "@material-ui/core/InputLabel";
// core components
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
import NativeSelect from "components/NativeSelect/NativeSelect.jsx";

import { CountryDropdown, RegionDropdown, CountryRegionData } from 'react-country-region-selector';

import avatar from "assets/img/faces/girl.jpg";

const styles = {

};

class UserProfileAccountSettings extends React.Component {
  constructor(props) {
      super(props);
      this.state = {value: ''};

      this.handleChange = this.handleChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
      this.setState({value: event.target.value});
    }

    handleSubmit(event) {
      alert('A name was submitted: ' + this.state.value);
      event.preventDefault();
    }

    render() {
      const { classes, user } = this.props;

      if (!user.id) {
        return null;
      }
      return (
          <GridContainer style={{ width: "100%" }}>
            <GridItem xs={12} sm={12} md={12}>
              <Card>
                <CardBody>
                  <GridContainer>
                    <GridItem xs={12} sm={12} md={4}>
                      <CustomInput
                        labelText="Email address"
                        id="email-address"
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          defaultValue: user.email
                        }}
                      />
                    </GridItem>
                    <GridItem xs={12} sm={12} md={4}>
                      <CustomInput
                        labelText="Old Password"
                        id="oldPassword"
                        formControlProps={{
                          fullWidth: true
                        }}
                      />
                    </GridItem>
                    <GridItem xs={12} sm={12} md={4}>
                      <CustomInput
                        labelText="New Password"
                        id="newPassword"
                        formControlProps={{
                          fullWidth: true
                        }}
                      />
                    </GridItem>
                  </GridContainer>
                </CardBody>
                <CardFooter>
                  <Button color="primary">Update Password</Button>
                </CardFooter>
              </Card>
            </GridItem>
          </GridContainer>
      );
    }
}
UserProfileAccountSettings.propTypes = {
  user: PropTypes.object.isRequired
};

export default withStyles(styles)(UserProfileAccountSettings);

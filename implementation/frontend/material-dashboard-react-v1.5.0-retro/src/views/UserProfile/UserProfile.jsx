import React from "react";
// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import Typography from '@material-ui/core/Typography';

// core components
import GridItem from "components/Grid/GridItem.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import Button from "components/CustomButtons/Button.jsx";
import Card from "components/Card/Card.jsx";
import CardAvatar from "components/Card/CardAvatar.jsx";
import CardBody from "components/Card/CardBody.jsx";
import UserProfileEditForm from "views/UserProfileEditForm/UserProfileEditForm.jsx";
import UserProfileAccountSettings from "views/UserProfileAccountSettings/UserProfileAccountSettings.jsx";

import LoginRepository from '../../api_calls/login.js';
import UserRepository from '../../api_calls/user.js';

import avatar from "assets/img/faces/girl.jpg";

import Cookies from "js-cookie";

const styles = {
};

class UserProfile extends React.Component {
  constructor(props) {
      super(props);
      this.state = {
        value: '',
        country: '',
        region: '',
        user: {
          id: 0,
          firstName: '',
          lastName: '',
          nickname: '',
          email: '',
          birthday: null,
          listOfLocations: [],
          gender: '',
          bio: ''
        }
     };

      this.handleChange = this.handleChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
      this.handleSubmitLogOut = this.handleSubmitLogOut.bind(this);
    }

    handleChange(event) {
      this.setState({
        value: event.target.value
      });
    }

    handleSubmit(event) {
      alert('A name was submitted: ' + this.state.value);
      event.preventDefault();
    }

    handleSubmitLogOut = event => {
      event.preventDefault();
      LoginRepository.logout()
        .then(res => {
          console.log(res);
          // window.location.reload();
        })
        .catch(err => {
          console.log(err);
        });
    }

    handleChangeField = (key, value) => {
      this.setState({
        user: {
          ...this.state.user,
          [key]: value
        }
      });
    }

    handleUpdatePressed = () => {
      UserRepository.updateProfile(this.state.user).then(_ => {}).catch(err => {
        console.error(err);
      });
    }

    componentDidMount() {
      UserRepository.user().then(user => {
        this.setState({user: user});
      });
    }

    render() {
      const { classes, country, region } = this.props;

      return (
        <div>
          <GridContainer>
            <GridItem xs={12} sm={12} md={12}>
              <Card profile>
                <CardAvatar profile>
                  <a href="#pablo" onClick={e => e.preventDefault()}>
                    <img src={avatar} alt="..." />
                  </a>
                </CardAvatar>
                <CardBody profile>
                  <h6> { this.state.user.firstName + " " + this.state.user.lastName } | { this.state.user.nickname } </h6>
                  <p>
                    { this.state.user.bio }
                  </p>
                  <h5>
                    { this.state.user.birthday }
                    {this.state.user.birthday !== null && this.state.user.gender !== 'NOT_TO_DISCLOSE' &&
                      <span>|</span>
                    }
                    { this.state.user.gender === 'NOT_TO_DISCLOSE' ? '' : this.state.user.gender }
                  </h5>
                </CardBody>
              </Card>
            </GridItem>
          </GridContainer>

          <GridContainer>
            <GridItem xs={12} sm={12} md={12}>
              <ExpansionPanel>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                  <Typography>Edit Profile</Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails>
                  <UserProfileEditForm onChangeField={this.handleChangeField}
                    user={ this.state.user } country="asdf" region="asdf"
                    onUpdatePressed={ this.handleUpdatePressed }/>
                </ExpansionPanelDetails>
              </ExpansionPanel>

              <ExpansionPanel>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                  <Typography>Account Settings</Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails>
                  <UserProfileAccountSettings user={ this.state.user } country="asdf" region="asdf" />
                </ExpansionPanelDetails>
              </ExpansionPanel>

              <ExpansionPanel>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                  <Typography>Log Out</Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails>
                  <Button color="primary" onClick={this.handleSubmitLogOut}>Log out</Button>
                </ExpansionPanelDetails>
              </ExpansionPanel>
            </GridItem>
          </GridContainer>

          <GridContainer>
            <GridItem xs={12} sm={12} md={12}>
              <Card profile>
                <CardBody profile>
                  <h6> memory </h6>
                </CardBody>
              </Card>
            </GridItem>
          </GridContainer>
        </div>
      );
    }
}
export default withStyles(styles)(UserProfile);

import React from "react";
// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import Typography from '@material-ui/core/Typography';

// core components
import CustomInput from "components/CustomInput/CustomInput.jsx";
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
import MediaRepository from '../../api_calls/media.js';

import Cookies from "js-cookie";
import { ProgressBar } from 'react-bootstrap';

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
          bio: '',
          birthday: null,
          email: '',
          firstName: '',
          gender: '',
          id: 0,
          lastName: '',
          listOfLocations: [
            {
              latitude: 0,
              locationName: '',
              longitude: 0
            }
          ],
          nickname: '',
          profilePictureUrl: ''
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
          window.location.reload();
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

    handleFileSelect = event => {this.setState({selectedFile: event.target.files[0]}) }

    handleFileUpload = () => {
      if(this.state.selectedFile != null) {
        const fd = new FormData();
        fd.append('media', this.state.selectedFile, this.state.selectedFile.name)
        MediaRepository.upload(fd, {
          onUploadProgress: progressEvent => {
            this.setState({ progress: Math.round((progressEvent.loaded / progressEvent.total)*100) })
          }
        })
          .then(res => this.handleChangeField(this.state.user.profilePictureUrl, res)); //here here
      }
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
                <GridItem xs={12} sm={12} md={12}>
                  <CardAvatar profile>
                    <a href="#pablo" onClick={e => e.preventDefault()}>
                      <img
                        className={classes.cardImgTop}
                        src={this.state.user.profilePictureUrl}
                      />
                    </a>
                  </CardAvatar>
                </GridItem>

                <GridItem xs={12} sm={12} md={12}>
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
                </GridItem>

                <GridItem xs={10} sm={10} md={4}>
                  <Card>
                    <GridItem xs={10} sm={10} md={12}>
                      Change or Upload Profile Picture
                      <CustomInput
                        id="media"
                        inputProps={{
                          name:"media",
                          type:"file",
                          onChange: this.handleFileSelect
                        }}
                        formControlProps={{
                          fullWidth: true,
                          required: false,
                        }}
                      />
                    </GridItem>
                    <GridItem xs={10} sm={10} md={12}>
                      <Button
                        onClick={this.handleFileUpload}
                        round
                        color="primary"
                      > Upload
                      </Button>
                      <ProgressBar now={this.progress} />
                    </GridItem>
                  </Card>
                </GridItem>
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

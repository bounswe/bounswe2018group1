import React from "react";
import PropTypes from "prop-types";
// @material-ui/core
import withStyles from "@material-ui/core/styles/withStyles";
// core components
import CustomInput from "components/CustomInput/CustomInput.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import Table from "components/Table/Table.jsx";
import Tasks from "components/Tasks/Tasks.jsx";
import Button from "components/CustomButtons/Button.jsx";
import CustomTabs from "components/CustomTabs/CustomTabs.jsx";
import Danger from "components/Typography/Danger.jsx";
import AccessTime from "@material-ui/icons/AccessTime";
import CommentIcon from "@material-ui/icons/Comment";

import Card from "components/Card/Card.jsx";
import CardHeader from "components/Card/CardHeader.jsx";
import CardBody from "components/Card/CardBody.jsx";
import CardFooter from "components/Card/CardFooter.jsx";

import image1 from "assets/img/selimiye.jpg";
import image2 from "assets/img/pamukkale.png";

import { bugs, website, server } from "variables/general.jsx";

import dashboardStyle from "assets/jss/material-dashboard-react/views/dashboardStyle.jsx";
import Like_CommentRepository from "api_calls/like_comment.js";
import MemoryRepository from "api_calls/memory_without_userId.js";
import UserRepository from "api_calls/user.js";

import LikePage from "views/Like_Comment/Like.jsx";
import CommentPage from "views/Like_Comment/Comment.jsx";

class ShowMemory extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
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
      },

      memory: {
        comments: [
          {
            dateOfCreation: "",
            id: 0,
            memoryId: 0,
            text: "",
            userFirstName: "",
            userId: 0,
            userLastName: "",
            userNickname: "",
            userProfilePicUrl: ""
          }
        ],
        id: null,
        dateOfCreation: "",
        endDateDD: 0,
        endDateHH: 0,
        endDateMM: 0,
        endDateYYYY: 0,
        headline: "",
        listOfItems: [
          {
            body: "",
            id: 0,
            url: ""
          }
        ],
        listOfLocations: [
          {
            latitude: 0,
            locationName: "",
            longitude: 0
          }
        ],
        listOfTags: [
          {
            text: ""
          }
        ],
        startDateDD: 0,
        startDateHH: 0,
        startDateMM: 0,
        startDateYYYY: 0,
        updatedTime: "",
        userFirstName: "",
        userId: 0,
        userLastName: "",
        userNickname: "",
        userProfilePicUrl: ""
      },
      contextMenu: null,
      addingAnnotation: false,
      annotationText: ""
    };

    this.handleDeleteComment = this.handleDeleteComment.bind(this);
    this.handleContextMenu = this.handleContextMenu.bind(this);
    this.handleContextMenuClose = this.handleContextMenuClose.bind(this);
    this.handleAddAnnotationClick = this.handleAddAnnotationClick.bind(this);
    this.handleResetAnnotation = this.handleResetAnnotation.bind(this);
  }

  handleDeleteComment(id) {
    Like_CommentRepository.deleteComment(id).then(memory => {
      console.log(memory);
    });
  }

  handleContextMenu(ev) {
    ev.preventDefault();
    ev.stopPropagation();

    this.setState({
      contextMenu: {
        x: ev.screenX - 260,
        y: ev.screenY + 50
      }
    });

    window.addEventListener("click", this.handleContextMenuClose);
  }

  handleContextMenuClose() {
    this.setState({
      contextMenu: null
    });

    window.removeEventListener("click", this.handleContextMenuClose);
  }

  handleAddAnnotationClick(ev) {
    ev.stopPropagation();

    const selection = window.getSelection();
    console.log(selection.getRangeAt(0));
    this.setState({
      contextMenu: null,
      addingAnnotation: true,
      selection: {
        text: selection.toString(),
        range: selection.getRangeAt(0)
      }
    });
  }

  handleResetAnnotation() {
    this.setState({
      contextMenu: null,
      addingAnnotation: false,
      selection: null
    });
  }

  // get memory çalışmalı
  componentDidMount() {
    const { id } = this.props;
    MemoryRepository.getMemory(id).then(memory => {
      console.log(memory);
      this.setState({memory: memory});
    });
    UserRepository.user().then(user => {
      this.setState({user: user});
    });

    window.addEventListener("click", this.handleResetAnnotation);
  }

  componentWillUnmount() {
    window.removeEventListener("click", this.handleResetAnnotation);
  }

  render() {
    const { classes } = this.props;

    const comms = this.state.memory.comments.sort(function(a, b) {
        return (new Date(a.dateOfCreation)).getTime() - (new Date(b.dateOfCreation)).getTime();
    });

    const creationDate = new Date(this.state.memory.dateOfCreation);


    return (
      <div>
        {this.state.contextMenu && (
          <div onClick={this.handleAddAnnotationClick} style={{ position: "absolute", background: "#fff", cursor: "pointer", userSelect: "none", top: this.state.contextMenu.y, padding: 5, border: "1px solid #000", left: this.state.contextMenu.x, zIndex: 9999 }}>
            Add annotation
          </div>
        )}
        <GridContainer>
          <GridItem xs={12} sm={12} md={8}>
            <Card>
              <CardHeader color="warning">
                <h4 className={classes.cardTitleWhite}>
                  {this.state.memory.headline}
                </h4>
                <p className={classes.cardCategoryWhite}>
                  Start Date:
                  {this.state.memory.startDateYYYY === '' ? '' : this.state.memory.startDateYYYY}
                  {this.state.memory.startDateMM === '' ? '' : '/' + this.state.memory.startDateMM}
                  {this.state.memory.startDateDD === '' ? '' : '/' + this.state.memory.startDateDD}
                  {this.state.memory.startDateHH === '' ? '' : ' - ' + this.state.memory.startDateHH}
                </p>
                <p className={classes.cardCategoryWhite}>
                  End Date:
                  {this.state.memory.endDateYYYY === '' ? '' : this.state.memory.endDateYYYY}
                  {this.state.memory.endDateMM === '' ? '' : '/' + this.state.memory.endDateMM}
                  {this.state.memory.endDateDD === '' ? '' : '/' + this.state.memory.endDateDD}
                  {this.state.memory.endDateHH === '' ? '' : ' - ' + this.state.memory.endDateHH}
                </p>
                <p className={classes.cardCategoryWhite}>
                  Added by {this.state.memory.userNickname}
                </p>
              </CardHeader>
              <CardBody>
                <p> Locations: </p>
                <ul>
                  {this.state.memory.listOfLocations.map((prop, key) => {
                      return (
                        <li> {prop.locationName} </li>
                      );
                  })}
                </ul>

                {this.state.memory.listOfItems.map((prop, key) => {
                  if (prop.type == 'PHOTO') {
                    return (
                      <img
                        className={classes.cardImgTop}
                        alt="100%x180"
                        style={{ height: "300px", width: "43%", display: "block" }}
                        src={prop.url}
                        data-holder-rendered="true"
                      />
                    );
                  } else if (prop.type == 'VIDEO') {
                    return (
                      true
                    );
                  } else if (prop.type == 'AUDIO') {
                    return (
                      true
                    );
                  } else {
                    return (
                      <p onContextMenu={this.handleContextMenu}> {prop.body} </p>
                    );
                  }
                })}

                {/* <img
                  className={classes.cardImgTop}
                  alt="100%x180"
                  style={{ height: "300px", width: "43%", display: "block" }}
                  src={image2}
                  data-holder-rendered="true"
                /> */}

                <p> Tags: </p>
                {this.state.memory.listOfTags.map((prop, key) => {
                  return (
                    <p> #{prop.text} </p>
                  );
                })}
              </CardBody>
              <CardFooter chart>
                <div className={classes.stats}>
                  <AccessTime />
                  added on {creationDate.getDate() + "-" + (creationDate.getMonth()+1) + "-" + creationDate.getFullYear() + " / " + ("0" + creationDate.getHours()).slice(-2) + ":" + ("0" + creationDate.getMinutes()).slice(-2)}
                </div>
              </CardFooter>

              <GridItem xs={10} sm={10} md={12}>
                <CardBody>
                  {comms.map((prop, key) => {
                    const date = new Date(prop.dateOfCreation);

                    return (
                      <GridContainer>
                        <GridItem xs={12} sm={12} md={12}>
                          <Card>
                            <CardHeader>
                              <GridItem xs={12} sm={12} md={12}>
                                <b>
                                  {prop.userFirstName} {prop.userLastName}
                                </b>
                              </GridItem>
                              <GridItem xs={12} sm={12} md={12}>
                                <p>
                                  {prop.text}
                                </p>
                              </GridItem>
                            </CardHeader>
                            <CardFooter chart>
                              <div className={classes.stats}>
                                <AccessTime />
                                {date.getDate() + "-" + (date.getMonth()+1) + "-" + date.getFullYear() + " / " + ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2)}
                              </div>
                            </CardFooter>
                            {(prop.userId === this.state.user.id) ?
                              <Button onClick={() => this.handleDeleteComment(prop.id)} round color="transparent">
                                <i class="far fa-trash-alt"></i>  Delete Comment
                              </Button>: null
                            }
                          </Card>
                        </GridItem>
                      </GridContainer>
                    );
                  })}
                </CardBody>
              </GridItem>

              <GridItem xs={12} sm={12} md={12}>
                <Card>
                  <CardBody>
                    <GridContainer>
                      <GridItem xs={12} sm={12} md={3}>
                        <LikePage memory={ this.state.memory } />
                      </GridItem>
                      <GridItem xs={12} sm={12} md={6}>
                        <CommentPage memory={ this.state.memory } />
                      </GridItem>
                    </GridContainer>
                  </CardBody>
                </Card>
              </GridItem>
            </Card>
          </GridItem>
          <GridItem xs={12} sm={12} md={4}>
            {this.state.addingAnnotation && (
              <div>
                <Card onClick={ev => ev.stopPropagation()}>
                  <CardBody>
                    <CustomInput
                      labelText={`Add annotation for ${this.state.selection.text.toString()}`}
                      id="annotationText"
                      value={this.state.annotationText}
                      inputProps={{
                        name:"annotationText",
                        type:"text",
                        onChange: event => this.setState({ annotationText: event.target.value }),
                      }}
                      formControlProps={{
                        fullWidth: true,
                        required: true
                      }}
                    />
                  </CardBody>
                </Card>
              </div>
            )}
          </GridItem>
        </GridContainer>
      </div>
    );
  }
}

ShowMemory.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(dashboardStyle)(ShowMemory);

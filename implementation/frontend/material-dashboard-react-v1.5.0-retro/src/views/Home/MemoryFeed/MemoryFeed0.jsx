import React from "react";
import PropTypes from "prop-types";
// @material-ui/core
import withStyles from "@material-ui/core/styles/withStyles";

// core components
import GridItem from "components/Grid/GridItem.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import Table from "components/Table/Table.jsx";
import Tasks from "components/Tasks/Tasks.jsx";
import CustomTabs from "components/CustomTabs/CustomTabs.jsx";
import Danger from "components/Typography/Danger.jsx";
import AccessTime from "@material-ui/icons/AccessTime";
import Card from "components/Card/Card.jsx";
import CardHeader from "components/Card/CardHeader.jsx";
import CardIcon from "components/Card/CardIcon.jsx";
import CardBody from "components/Card/CardBody.jsx";
import CardFooter from "components/Card/CardFooter.jsx";
import Button from "components/CustomButtons/Button.jsx";
import PVideo from "components/PVideo/index.js";

import image1 from "assets/img/selimiye.jpg";
import image2 from "assets/img/pamukkale.png";

import { bugs, website, server } from "variables/general.jsx";

import dashboardStyle from "assets/jss/material-dashboard-react/views/dashboardStyle.jsx";
import MemoryRepository from "api_calls/memory_without_userId.js";
import UserRepository from 'api_calls/user.js';

class MemoryFeed extends React.Component {
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
      listOfMemories: []
    };
  }
  handleChange = (event, value) => {
    this.setState({ value });
  };

  handleChangeIndex = index => {
    this.setState({ value: index });
  };

  componentDidMount() {
    UserRepository.user().then(user => {
      this.setState({user: user});
    });
    MemoryRepository.getAllMemories().then(listOfMemories => {
      this.setState({ listOfMemories: listOfMemories.content });
    });
  }

  render() {
    const { classes } = this.props;
    const { listOfMemories } = this.state;
    var memoryList = (
      <GridContainer>
        {listOfMemories.filter(memory => { return (this.state.user.id === memory.userId) }).map((prop, key) => {
          return (
            <GridItem xs={12} sm={12} md={6}>
              <Card>
                <CardHeader color="warning">
                  <h4 className={classes.cardTitleWhite}> {prop.headline}</h4>
                  <p className={classes.cardCategoryWhite}>
                    Start Date:
                    {prop.startDateYYYY === '' ? '' : prop.startDateYYYY}
                    {prop.startDateMM === '' ? '' : '/' + prop.startDateMM}
                    {prop.startDateDD === '' ? '' : '/' + prop.startDateDD}
                    {prop.startDateHH === '' ? '' : ' - ' + prop.startDateHH}
                  </p>
                  <p className={classes.cardCategoryWhite}>
                    {prop.endDateYYYY === '' ? '' : 'End Date: ' + prop.endDateYYYY}
                    {prop.endDateMM === '' ? '' : '/' + prop.endDateMM}
                    {prop.endDateDD === '' ? '' : '/' + prop.endDateDD}
                    {prop.endDateHH === '' ? '' : ' - ' + prop.endDateHH}
                  </p>
                  <p className={classes.cardCategoryWhite}>Added by {prop.userNickname}</p>
                </CardHeader>
                <CardBody>
                  <p> Locations: </p>
                  <ul>
                    {prop.listOfLocations.map((propOfLocation, keyOfLocation) => {
                        return (
                          <li> {propOfLocation.locationName} </li>
                        );
                    })}
                  </ul>

                  {prop.listOfItems.map((propOfItems, keyOfItems) => {
                    if (propOfItems.type === 'VIDEO') {
                      return (
                        <PVideo
                          key={keyOfItems}
                          url={propOfItems.url}
                        />
                      );
                    } else if (propOfItems.type === 'PHOTO') {
                      return (
                        <img
                          className={classes.cardImgTop}
                          alt="100%x180"
                          style={{ height: "300px", width: "60%", display: "block" }}
                          src={propOfItems.url}
                          data-holder-rendered="true"
                        />
                      );
                    } else {
                      return (
                        <p> {propOfItems.body} </p>
                      );
                    }


                  })}
                  <p> Tags: </p>
                  {prop.listOfTags.map((propOfTags, keyOfTags) => {
                    return (
                      <p> #{propOfTags.text} </p>
                    );
                  })}
                </CardBody>
                <CardFooter chart>
                  <div className={classes.stats}>
                    <AccessTime /> added on {prop.updatedTime}
                  </div>
                </CardFooter>
              </Card>
            </GridItem>
          );
        })}
      </GridContainer>
    );
    return (
      <div>
        <GridContainer>{memoryList}</GridContainer>
      </div>
    );
  }
}

MemoryFeed.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(dashboardStyle)(MemoryFeed);

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
import CardBody from "components/Card/CardBody.jsx";
import CardFooter from "components/Card/CardFooter.jsx";

import image1 from "assets/img/selimiye.jpg";
import image2 from "assets/img/pamukkale.png";

import { bugs, website, server } from "variables/general.jsx";

import dashboardStyle from "assets/jss/material-dashboard-react/views/dashboardStyle.jsx";
import MemoryRepository from "api_calls/memory_without_userId.js";

class ShowMemory extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      memory: {
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
      }
    };
  }

  // get memory çalışmalı

  componentDidMount() {
    MemoryRepository.getMemory().then(memory => {
      this.setState({memory: memory});
    });
  }

  render() {
    const { classes } = this.props;
    return (
      <div>
        <GridContainer>
          <GridItem xs={12} sm={12} md={12}>
            <Card>
              <CardHeader color="warning">
                <h4 className={classes.cardTitleWhite}>
                  {this.state.memory.headline}
                </h4>
                <p className={classes.cardCategoryWhite}>
                  Start Date:
                  {this.state.memory.startDateYYYY == '' ? '' : this.state.memory.startDateYYYY}
                  {this.state.memory.startDateMM == '' ? '' : '/' + this.state.memory.startDateMM}
                  {this.state.memory.startDateDD == '' ? '' : '/' + this.state.memory.startDateDD}
                  {this.state.memory.startDateHH == '' ? '' : ' - ' + this.state.memory.startDateHH}
                </p>
                <p className={classes.cardCategoryWhite}>
                  End Date:
                  {this.state.memory.endDateYYYY == '' ? '' : this.state.memory.endDateYYYY}
                  {this.state.memory.endDateMM == '' ? '' : '/' + this.state.memory.endDateMM}
                  {this.state.memory.endDateDD == '' ? '' : '/' + this.state.memory.endDateDD}
                  {this.state.memory.endDateHH == '' ? '' : ' - ' + this.state.memory.endDateHH}
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

                {/* listOfItems dönülecek ve içindeki her şey basılacak */}

                {this.state.memory.listOfItems.map((prop, key) => {
                  if (prop.body === '') {
                    return (
                      <p> {prop.url} </p>
                    );
                  } else {
                    return (
                      <p> {prop.body} </p>
                    );
                  }
                })}

                {/* <img
                  className={classes.cardImgTop}
                  alt="100%x180"
                  style={{ height: "300px", width: "33%", display: "block" }}
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
                  added on {this.state.memory.dateOfCreation}
                </div>
              </CardFooter>
            </Card>
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

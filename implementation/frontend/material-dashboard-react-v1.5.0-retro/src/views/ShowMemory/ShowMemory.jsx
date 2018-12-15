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
import MemoryRepository from '../../api_calls/memory.js';

class ShowMemory extends React.Component {
  constructor(props) {
      super(props);
      this.state = {
          memory: {
            dateOfCreation: '',
            endDateDD: 0,
            endDateHH: 0,
            endDateMM: 0,
            endDateYYYY: 0,
            headline: '',
            listOfItems: [
              {
                body: '',
                id: 0,
                url: ''
              }
            ],
            listOfLocations: [
              {
                latitude: 0,
                locationName: '',
                longitude: 0
              }
            ],
            listOfTags: [
              {
                text: ''
              }
            ],
            startDateDD: 0,
            startDateHH: 0,
            startDateMM: 0,
            startDateYYYY: 0,
            updatedTime: '',
            userFirstName: '',
            userId: 0,
            userLastName: '',
            userNickname: '',
            userProfilePicUrl: ''
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
                <h4 className={classes.cardTitleWhite}>{this.state.memory.headline}</h4>
                <p className={classes.cardCategoryWhite}>
                  Start Date: 2018.10.19.11:00
                </p>
                <p className={classes.cardCategoryWhite}>
                  End Date: 2018.10.19.17:00
                </p>
                <p className={classes.cardCategoryWhite}>
                  Added by BuseEce20
                </p>
              </CardHeader>
              <CardBody>
              <p> Locations: </p>

{/* listOfLocations dönülecek ve içindeki her şey basılacak */}

              <ul>
                <li> Pamukkale, Denizli </li>
                <li> Denizli, Turkey </li>
              </ul>

{/* listOfItems dönülecek ve içindeki her şey basılacak */}

              <p> We had a wonderful time together with our Cmpe451 group. Travertines are not very crowded early in the morning so there are cool photo opportunities. But you must see them while the sun
              sets, it is a gorgeous view.</p>
              <img
                className={classes.cardImgTop}
                alt="100%x180"
                style={{ height: "300px", width: "33%", display: "block" }}
                src={image2}
                data-holder-rendered="true"
              />

{/* listOfTags dönülecek ve içindeki her şey basılacak */}

              <p> Tags: #pamukkale    #friends</p>

              </CardBody>
              <CardFooter chart>
                <div className={classes.stats}>
                  <AccessTime /> added on 2018.10.23
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

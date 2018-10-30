import React from "react";
import PropTypes from "prop-types";
// react plugin for creating charts
import ChartistGraph from "react-chartist";
// @material-ui/core
import withStyles from "@material-ui/core/styles/withStyles";
import Icon from "@material-ui/core/Icon";
// @material-ui/icons
import Store from "@material-ui/icons/Store";
import Warning from "@material-ui/icons/Warning";
import DateRange from "@material-ui/icons/DateRange";
import LocalOffer from "@material-ui/icons/LocalOffer";
import Update from "@material-ui/icons/Update";
import ArrowUpward from "@material-ui/icons/ArrowUpward";
import AccessTime from "@material-ui/icons/AccessTime";
import Accessibility from "@material-ui/icons/Accessibility";
import BugReport from "@material-ui/icons/BugReport";
import Code from "@material-ui/icons/Code";
import Cloud from "@material-ui/icons/Cloud";
// core components
import GridItem from "components/Grid/GridItem.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import Table from "components/Table/Table.jsx";
import Tasks from "components/Tasks/Tasks.jsx";
import CustomTabs from "components/CustomTabs/CustomTabs.jsx";
import Danger from "components/Typography/Danger.jsx";
import Card from "components/Card/Card.jsx";
import CardHeader from "components/Card/CardHeader.jsx";
import CardIcon from "components/Card/CardIcon.jsx";
import CardBody from "components/Card/CardBody.jsx";
import CardFooter from "components/Card/CardFooter.jsx";

import image1 from "assets/img/camlik.png";
import image2 from "assets/img/pamukkale.png";

import { bugs, website, server } from "variables/general.jsx";

import {
  dailySalesChart,
  emailsSubscriptionChart,
  completedTasksChart
} from "variables/charts.jsx";

import dashboardStyle from "assets/jss/material-dashboard-react/views/dashboardStyle.jsx";

class Story extends React.Component {
  state = {
    value: 0
  };
  handleChange = (event, value) => {
    this.setState({ value });
  };

  handleChangeIndex = index => {
    this.setState({ value: index });
  };

  render() {
    const { classes } = this.props;
    return (
      <div>
        <GridContainer>
          <GridItem xs={12} sm={12} md={6}>
            <Card chart>
              <img
                className={classes.cardImgTop}
                alt="100%x180"
                style={{ height: "300px", width: "100%", display: "block" }}
                src={image1}
                data-holder-rendered="true"
              />
              <CardBody>
                <h4 className={classes.cardTitle}>Çamlık</h4>
                <p className={classes.cardCategory}>
                  <span className={classes.successText}>
                  </span>{" "}
                  Denizli is a town with tree-lined main avenues and views of the surrounding mountains from many locations. As the city grew in the 1990s, new compounds of villas have sprung up on the city's outskirts in areas like Çamlık.
                </p>
              </CardBody>
              <CardFooter chart>
                <div className={classes.stats}>
                  <AccessTime /> added 2 minutes ago
                </div>
              </CardFooter>
            </Card>
          </GridItem>
          <GridItem xs={12} sm={12} md={6}>
            <Card chart>
              <img
                className={classes.cardImgTop}
                alt="100%x180"
                style={{ height: "400px", width: "100%", display: "block" }}
                src={image2}
                data-holder-rendered="true"
              />
              <CardBody>
                <h4 className={classes.cardTitle}>Pamukkale Travertine</h4>
                <p className={classes.cardCategory}>
                  Pamukkale, meaning cotton castle in Turkish, is a natural site in Denizli in southwestern Turkey. The area is famous for a carbonate mineral left by the flowing water.[1] It is located in Turkey's Inner Aegean region, in the River Menderes valley, which has a temperate climate for most of the year.
                </p>
              </CardBody>
              <CardFooter chart>
                <div className={classes.stats}>
                  <AccessTime /> added 4 minutes ago
                </div>
              </CardFooter>
            </Card>
          </GridItem>
        </GridContainer>
      </div>
    );
  }
}


Story.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(dashboardStyle)(Story);

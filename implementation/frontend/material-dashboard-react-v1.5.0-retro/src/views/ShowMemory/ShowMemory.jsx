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
import Card from "components/Card/Card.jsx";
import CardHeader from "components/Card/CardHeader.jsx";
import CardIcon from "components/Card/CardIcon.jsx";
import CardBody from "components/Card/CardBody.jsx";
import CardFooter from "components/Card/CardFooter.jsx";

import { bugs, website, server } from "variables/general.jsx";

import {
  dailySalesChart,
  emailsSubscriptionChart,
  completedTasksChart
} from "variables/charts.jsx";

import dashboardStyle from "assets/jss/material-dashboard-react/views/dashboardStyle.jsx";

class ShowMemory extends React.Component {
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
          <GridItem xs={12} sm={12} md={12}>
            <Card>
              <CardHeader color="warning">
                <h4 className={classes.cardTitleWhite}>Denizli tour</h4>
                <p className={classes.cardCategoryWhite}>
                  The best places of Denizli :)
                </p>
              </CardHeader>
              <CardBody>
                <Table
                  tableHeaderColor="warning"
                  tableHead={["Story Name", "Start Date", "End Date", "Location", "Description"]}
                  tableData={[
                    ["Çamlık Parkı", "2018.10.19", "2018.10.19", "Denizli, Turkey", "Denizli is a town with tree-lined..."],
                    ["Pamukkale", "2018.10.23", "2018.10.23", "Denizli, Turkey", "Pamukkale, meaning cotton castle..."]
                  ]}
                />
              </CardBody>
            </Card>
          </GridItem>
        </GridContainer>
        <GridContainer>
          <GridItem xs={12} sm={12} md={12}>
            <Card>
              <CardHeader color="warning">
                <h4 className={classes.cardTitleWhite}>Cmpe 443 Midterm</h4>
                <p className={classes.cardCategoryWhite}>
                  The hardest exam ever!
                </p>
              </CardHeader>
              <CardBody>
                <Table
                  tableHeaderColor="warning"
                  tableHead={["Story Name", "Start Date", "End Date", "Location", "Description"]}
                  tableData={[
                    ["Midterm 1", "2018.10.18", "2018.10.18", "İstanbul, Turkey", "Midterm topics are..."]
                  ]}
                />
              </CardBody>
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

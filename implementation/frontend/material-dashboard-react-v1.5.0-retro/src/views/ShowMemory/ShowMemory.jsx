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
                <h4 className={classes.cardTitleWhite}>Our little Denizli tour</h4>
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
              <ul>
                <li> Pamukkale, Denizli </li>
                <li> Denizli, Turkey </li>
              </ul>
              <p> We had a wonderful time together with our Cmpe451 group. Travertines are not very crowded early in the morning so there are cool photo opportunities. But you must see them while the sun
              sets, it is a gorgeous view.</p>
              <img
                className={classes.cardImgTop}
                alt="100%x180"
                style={{ height: "300px", width: "33%", display: "block" }}
                src={image2}
                data-holder-rendered="true"
              />
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

        <GridContainer>
          <GridItem xs={12} sm={12} md={12}>
            <Card>
              <CardHeader color="warning">
                <h4 className={classes.cardTitleWhite}>Our big Edirne Tour</h4>
                <p className={classes.cardCategoryWhite}>
                  Start Date: 2018.07.18.18:00
                </p>
                <p className={classes.cardCategoryWhite}>
                  End Date: 2018.07.18.20:00
                </p>
                <p className={classes.cardCategoryWhite}>
                  Added by EceAta20
                </p>
              </CardHeader>
              <CardBody>
              <p> Locations: </p>
              <ul>
                <li> Edirne Merkez/Edirne </li>
                <li> Edirne, Turkey </li>
                <li> Selimiye Mosque, Turkey </li>
              </ul>
              <p> Edirne has many historical places like Selimiye Mosque, but not a single one is famed more. Designed by Mimar Sinan, famous architect of the Ottoman Empire
              by orders from Selim II (son of Suleyman the Magnificent). It has very tall minarets and 999 windows, we confirmed it by counting ourselves. </p>
              <img
                className={classes.cardImgTop}
                alt="100%x180"
                style={{ height: "300px", width: "33%", display: "block" }}
                src={image1}
                data-holder-rendered="true"
              />
              <p> Tags: #religion    #peace</p>
              </CardBody>
              <CardFooter chart>
                <div className={classes.stats}>
                  <AccessTime /> added on 2018.07.19
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

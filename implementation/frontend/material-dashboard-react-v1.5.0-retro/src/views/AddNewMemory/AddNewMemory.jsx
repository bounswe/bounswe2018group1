import React from "react";
// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
// @material-ui/icons
import Close from "@material-ui/icons/Close";
import Check from "@material-ui/icons/Check";
// core components
import CustomInput from "components/CustomInput/CustomInput.jsx";
import DateInput from "components/DateInput/DateInput.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import Danger from "components/Typography/Danger.jsx";
import Success from "components/Typography/Success.jsx";
import Button from "components/CustomButtons/Button.jsx";
import Card from "components/Card/Card.jsx";
import CardHeader from "components/Card/CardHeader.jsx";
import CardBody from "components/Card/CardBody.jsx";

const styles = {
  cardCategoryWhite: {
    "&,& a,& a:hover,& a:focus": {
      color: "rgba(255,255,255,.62)",
      margin: "0",
      fontSize: "14px",
      marginTop: "0",
      marginBottom: "0"
    },
    "& a,& a:hover,& a:focus": {
      color: "#FFFFFF"
    }
  },
  cardTitleWhite: {
    color: "#FFFFFF",
    marginTop: "0px",
    minHeight: "auto",
    fontWeight: "300",
    fontFamily: "'Roboto', 'Helvetica', 'Arial', sans-serif",
    marginBottom: "3px",
    textDecoration: "none",
    "& small": {
      color: "#777",
      fontSize: "65%",
      fontWeight: "400",
      lineHeight: "1"
    }
  },
  tableUpgradeWrapper: {
    display: "block",
    width: "100%",
    overflowX: "auto",
    WebkitOverflowScrolling: "touch",
    MsOverflowStyle: "-ms-autohiding-scrollbar"
  },
  table: {
    width: "100%",
    maxWidth: "100%",
    marginBottom: "1rem",
    backgroundColor: "transparent",
    borderCollapse: "collapse",
    display: "table",
    borderSpacing: "2px",
    borderColor: "grey",
    "& thdead tr th": {
      fontSize: "1.063rem",
      padding: "12px 8px",
      verticalAlign: "middle",
      fontWeight: "300",
      borderTopWidth: "0",
      borderBottom: "1px solid rgba(0, 0, 0, 0.06)",
      textAlign: "inherit"
    },
    "& tbody tr td": {
      padding: "12px 8px",
      verticalAlign: "middle",
      borderTop: "1px solid rgba(0, 0, 0, 0.06)"
    },
    "& td, & th": {
      display: "table-cell"
    }
  },
  center: {
    textAlign: "center"
  }
};

function AddNewMemory(props) {
  const { classes } = props;
  return (
    <GridContainer justify="center">
      <GridItem xs={12} sm={12} md={10}>
        <Card>
          <CardHeader color="info">
            <h4 className={classes.cardTitleWhite}>
              Add a new memory
            </h4>
            <p className={classes.cardCategoryWhite}>
              Do you want to share your memory? Please fill the form.
            </p>
          </CardHeader>
          <CardBody>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Title"
                  id="title"
                  formControlProps={{
                    fullWidth: true
                  }}
                />
              </GridItem>
            </GridContainer>
            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Description"
                  id="description"
                  formControlProps={{
                    fullWidth: true
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Image"
                  id="image"
                  formControlProps={{
                    fullWidth: true
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={4}>
                <span>Start Time</span>
                <CustomInput
                  id="startTime"
                  inputProps={{type: "date"}}
                  formControlProps={{
                    fullWidth: true
                  }}
                />
              </GridItem>
              <GridItem xs={10} sm={10} md={4}>
                <span>End Time</span>
                <CustomInput
                  id="endTime"
                  inputProps={{type: "date"}}
                  formControlProps={{
                    fullWidth: true
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Location"
                  id="location"
                  formControlProps={{
                    fullWidth: true
                  }}
                />
              </GridItem>
            </GridContainer>

            <tr>
              <td className={classes.center}>
              <Button
                round
                color="info"
              >Add New Memory
              </Button>
              </td>
            </tr>

          </CardBody>
        </Card>
      </GridItem>
    </GridContainer>
  );
}

export default withStyles(styles)(AddNewMemory);

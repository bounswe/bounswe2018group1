import React, { Component } from "react";
// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";

// core components
import CustomInput from "components/CustomInput/CustomInput.jsx";
import DateInput from "components/DateInput/DateInput.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import Button from "components/CustomButtons/Button.jsx";
import Card from "components/Card/Card.jsx";
import CardHeader from "components/Card/CardHeader.jsx";
import CardBody from "components/Card/CardBody.jsx";

import MemoryRepository from '../../api_calls/memory.js';

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

export default class AddNewMemory extends Component {
  constructor(props) {
    super(props);

    this.state = {
      headline: "",
      endDateDD: 0,
      endDateHH: 0,
      endDateMM: 0,
      endDateYYYY: 0,
      startDateDD: 0,
      startDateHH: 0,
      startDateMM: 0,
      startDateYYYY: 0,
      listOfItems: [],
      listOfLocations: [],
      listOfTags: []
    };
    //this.handleChange = this.handleChange.bind(this);
  }

  handleAddNewMemory = event => {
    event.preventDefault();
    //MemoryRepository.createMemory(listOfItems, listOfLocations, listOfTags, headline, endDateDD, endDateHH, endDateMM, endDateYYYY, startDateDD, startDateHH, startDateMM, startDateYYYY)
    MemoryRepository.createMemory(this.state.listOfItems, this.state.listOfLocations, this.state.listOfTags, this.state.headline, this.state.endDateDD, this.state.endDateHH, this.state.endDateMM,
      this.state.endDateYYYY, this.state.startDateDD, this.state.startDateHH, this.state.startDateMM, this.state.startDateYYYY)
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });
      // TODO: Bunu res in icine tasi.
      const { history } = this.props;
      history.push("/show-memory");
  }

 render() {
  return (
    <GridContainer justify="center">
      <GridItem xs={12} sm={12} md={10}>
        <Card>
          <CardHeader color="info">
            <h4 className="cardTitleWhite">
              Add a new memory
            </h4>
            <p className="cardCategoryWhite">
              Do you want to share your memory? Please fill the form.
            </p>
          </CardHeader>
          <CardBody>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Title"
                  id="headline"
                  value={this.state.headline}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: true
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Add the details of your memory"
                  id="items"
                  value={this.state.listOfItems}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Add locations to your memory"
                  id="items"
                  value={this.state.listOfLocations}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Enter your tags"
                  id="tags"
                  value={this.state.listOfTags}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Year (YYYY)"
                  id="start_date_yyyy"
                  value={this.state.startDateYYYY}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: true
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Month (1-12)"
                  id="start_date_mm"
                  value={this.state.startDateMM}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Day (1-31)"
                  id="start_date_dd"
                  value={this.state.startDateDD}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Hour (0-23)"
                  id="start_date_hh"
                  value={this.state.startDateHH}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Year (YYYY)"
                  id="end_date_yyyy"
                  value={this.state.endDateYYYY}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: true
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Month (1-12)"
                  id="end_date_mm"
                  value={this.state.endtDateMM}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Day (1-31)"
                  id="end_date_dd"
                  value={this.state.endDateDD}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Hour (0-23)"
                  id="end_date_hh"
                  value={this.state.endDateHH}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
            </GridContainer>

            <tr>
              <td className="center">
              <Button
                onClick={this.handleAddNewMemory}
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
}

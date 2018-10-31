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
      description: "",
      headline: "",
      storyList: [{city: "", country: "", county: "", district: "", headline: "", description: "", locationDto:{latitude: 0, longitude: 0}, time: 0}]
    };
    //this.handleChange = this.handleChange.bind(this);
  }

  handleAddNewMemory = event => {
    event.preventDefault();
    MemoryRepository.createMemory(this.state.description, this.state.headline, this.state.storyList)
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

  handleAddStory = event => {
    event.preventDefault();
    // MemoryRepository.createStory(this.state.description, this.state.headline, this.state.storyList)
    //   .then(res => {
    //     console.log(res);
    //   })
    //   .catch(err => {
    //     console.log(err);
    //   });
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
                  labelText="Description"
                  id="description"
                  value={this.state.description}
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
              <GridItem xs={10} sm={10} md={4}>
                <DateInput
                  id="startTime"
                  labelText="Start Time"
                  className="textField"
                  formControlProps={{
                    fullWidth: true
                  }}
                />
              </GridItem>
              <GridItem xs={10} sm={10} md={4}>
                <DateInput
                  id="endTime"
                  labelText="End Time"
                  className="textField"
                  formControlProps={{
                    fullWidth: true
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="City"
                  id="city"
                  value={this.state.storyList.city}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="County"
                  id="county"
                  value={this.state.storyList.county}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="District"
                  id="district"
                  value={this.state.storyList.district}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Country"
                  id="country"
                  value={this.state.storyList.country}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
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
                  id="locationDto"
                  formControlProps={{
                    fullWidth: true
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Story Headline"
                  id="storyHeadline"
                  value={this.state.storyList.headline}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  labelText="Story Description"
                  id="stroyDescription"
                  value={this.state.storyList.description}
                  inputProps={{
                    type:"text",
                    onChange: this.handleChange
                  }}
                  formControlProps={{
                    fullWidth: true
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
              <td className="center">
              <Button
                onClick={this.handleAddStory}
                round
                color="transparent"
              >Add More Story
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

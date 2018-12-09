import React, { Component } from "react";
// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
// core components
import CustomInput from "components/CustomInput/CustomInput.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import Button from "components/CustomButtons/Button.jsx";
import Card from "components/Card/Card.jsx";
import CardHeader from "components/Card/CardHeader.jsx";
import CardBody from "components/Card/CardBody.jsx";
import CardFooter from "components/Card/CardFooter.jsx";
import CountrySelect from "components/CountrySelect/CountrySelect.jsx";
import Icon from '@material-ui/core/Icon';
import { WithContext as ReactTags } from 'react-tag-input';
import { ProgressBar } from 'react-bootstrap';

import MemoryRepository from '../../api_calls/memory.js';
import MediaRepository from '../../api_calls/media.js';

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
      tags: [],
      selectedFile: null,
      progress: 0
    };
    this.handleAddNewLocation = this.handleAddNewLocation.bind(this);
    this.handleDelete = this.handleDelete.bind(this);
    this.handleAddition = this.handleAddition.bind(this);
    this.handleDrag = this.handleDrag.bind(this);
  }

  handleAddNewMemory = event => {
    event.preventDefault();
    console.log(this.state); //Remove after development is complete.
    var listOfTags = this.state.tags.map((prop, key) => { return {text: prop.text}});
    //MemoryRepository.createMemory(listOfItems, listOfLocations, listOfTags, headline, endDateDD, endDateHH, endDateMM, endDateYYYY, startDateDD, startDateHH, startDateMM, startDateYYYY)
    MemoryRepository.createMemory(this.state.listOfItems, this.state.listOfLocations, listOfTags, this.state.headline, this.state.endDateDD, this.state.endDateHH, this.state.endDateMM,
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

  handleAddNewLocation() {
    this.setState({
      listOfLocations: [
        ...this.state.listOfLocations,
        {
          "latitude": 0,
          "locationName": "",
          "longitude": 0
        }
      ]
    });
  }

  handleAddNewMedia() {
    this.setState({
      listOfItems: [
        ...this.state.listOfItems,
        {
          "body": "",
          "id": 0,
          "url": ""
        }
      ]
    });
  }

  handleFileSelect = event => {this.setState({selectedFile: event.target.files[0]}) }

  handleFileUpload = () => {
    if(this.state.selectedFile != null) {
      const fd = new FormData();
      fd.append('media', this.state.selectedFile, this.state.selectedFile.name)
      MediaRepository.upload(fd, {
        onUploadProgress: progressEvent => {
          this.setState({ progress: Math.round((progressEvent.loaded / progressEvent.total)*100) })
        }
      })
        .then(res => {});
    }
  }

  handleDelete(i) {
      const { tags } = this.state;
      this.setState({
       tags: tags.filter((tag, index) => index !== i),
      });
  }

  handleAddition(tag) {
      this.setState(state => ({ tags: [...state.tags, tag] }));
  }

  handleDrag(tag, currPos, newPos) {
      const tags = [...this.state.tags];
      const newTags = tags.slice();

      newTags.splice(currPos, 1);
      newTags.splice(newPos, 0, tag);

      // re-render
      this.setState({ tags: newTags });
  }

 render() {
   const Keys = {
    TAB: 9,
    SPACE: 32,
    COMMA: 188,
    };

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
                    name:"headline",
                    type:"text",
                    onChange: event => this.setState({ headline: event.target.value }),
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
                  id="details"
                  inputProps={{
                    name:"details",
                    type:"text"
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: true
                  }}
                />
              </GridItem>

              <GridItem xs={10} sm={10} md={8}>
                <CustomInput
                  id="media"
                  inputProps={{
                    name:"media",
                    type:"file",
                    onChange: this.handleFileSelect
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false,
                  }}
                  />
              <Button onClick={this.handleFileUpload}> Upload </Button>
              <ProgressBar now={this.progress} />
              </GridItem>

              {/*<GridItem xs={10} sm={10} md={10}>
              Add more...
                <Button
                  onClick={this.handleAddNewMedia}
                  justIcon
                  color="transparent"
                >
                  <Icon style={{ fontSize: 30 }}>
                    add_circle
                  </Icon>
                </Button>
              </GridItem>*/}
            </GridContainer>



            <GridContainer>
              <GridItem xs={10} sm={10} md={4}>
                <CountrySelect
                  id="country"
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              {this.state.listOfLocations.map( (location, i) => (
                  <CountrySelect
                    key={i}
                    id="country"
                    formControlProps={{
                      fullWidth: true,
                      required: false
                    }}
                  />
                ))
                }
              </GridItem>
              <GridItem xs={10} sm={10} md={10}>
                <Button
                  onClick={this.handleAddNewLocation}
                  justIcon
                  color="transparent"
                >
                  <Icon style={{ fontSize: 30 }}>
                    add_circle
                  </Icon>
                </Button>
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={8}>

                <ReactTags
                    handleDelete={this.handleDelete}
                    handleAddition={this.handleAddition}
                    handleDrag={this.handleDrag}
                    delimiters={[Keys.TAB, Keys.SPACE, Keys.COMMA]}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={2}>
                <CustomInput
                  labelText="Year (YYYY)"
                  id="start_date_yyyy"
                  value={this.state.startDateYYYY}
                  inputProps={{
                    name:"startDateYYYY",
                    type:"text",
                    onChange: event => this.setState({ startDateYYYY: event.target.value }),
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: true
                  }}
                />
              </GridItem>
              <GridItem xs={10} sm={10} md={2}>
                <CustomInput
                  labelText="Month (1-12)"
                  id="start_date_mm"
                  value={this.state.startDateMM}
                  inputProps={{
                    name:"startDateMM",
                    type:"text",
                    onChange: event => this.setState({ startDateMM: event.target.value }),
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
              <GridItem xs={10} sm={10} md={2}>
                <CustomInput
                  labelText="Day (1-31)"
                  id="start_date_dd"
                  value={this.state.startDateDD}
                  inputProps={{
                    name:"startDateDD",
                    type:"text",
                    onChange: event => this.setState({ startDateDD: event.target.value }),
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
              <GridItem xs={10} sm={10} md={2}>
                <CustomInput
                  labelText="Hour (0-23)"
                  id="start_date_hh"
                  value={this.state.startDateHH}
                  inputProps={{
                    name:"startDateHH",
                    type:"text",
                    onChange: event => this.setState({ startDateHH: event.target.value }),
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={2}>
                <CustomInput
                  labelText="Year (YYYY)"
                  id="end_date_yyyy"
                  value={this.state.endDateYYYY}
                  inputProps={{
                    name:"endDateYYYY",
                    type:"text",
                    onChange: event => this.setState({ endDateYYYY: event.target.value }),
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
              <GridItem xs={10} sm={10} md={2}>
                <CustomInput
                  labelText="Month (1-12)"
                  id="end_date_mm"
                  value={this.state.endtDateMM}
                  inputProps={{
                    name:"endtDateMM",
                    type:"text",
                    onChange: event => this.setState({ endtDateMM: event.target.value }),
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
              <GridItem xs={10} sm={10} md={2}>
                <CustomInput
                  labelText="Day (1-31)"
                  id="end_date_dd"
                  value={this.state.endDateDD}
                  inputProps={{
                    name:"endDateDD",
                    type:"text",
                    onChange: event => this.setState({ endDateDD: event.target.value }),
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
              <GridItem xs={10} sm={10} md={2}>
                <CustomInput
                  labelText="Hour (0-23)"
                  id="end_date_hh"
                  value={this.state.endDateHH}
                  inputProps={{
                    name:"endDateHH",
                    type:"text",
                    onChange: event => this.setState({ endDateHH: event.target.value }),
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
            </GridContainer>
          </CardBody>
          <CardFooter>
          <Button onClick={this.handleAddNewMemory} round color="info"> Add New Memory </Button>
          </CardFooter>
        </Card>
      </GridItem>
    </GridContainer>
    );
  }
}
//
// AddNewMemory.propTypes = {
//
// };
// export default withStyles(styles)(AddNewMemory);

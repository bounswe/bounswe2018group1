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
import { ProgressBar } from 'react-bootstrap';
import ListSubheader from '@material-ui/core/ListSubheader';

import MemoryRepository from '../../api_calls/memory_with_userId.js';
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
};

const index = 3;

function NewMedia({ item, handleFileSelect, onChangeText }) {
  if (item.type === 'PHOTO') {
    return (
      <Card>
        <CardBody>
            Add photo
            <CustomInput
              labelText="Add photo"
              id="media"
              inputProps={{
                name:"media",
                type:"file",
                onChange: handleFileSelect
              }}
              formControlProps={{
                fullWidth: true,
                required: false,
              }}
              />
        </CardBody>
      </Card>
    );
  } else if (item.type === 'VIDEO') {
    return (
      <Card>
        <CardBody>
          Add video
          <CustomInput
            labelText="Add video"
            id="media"
            inputProps={{
              name:"media",
              type:"file",
              onChange: handleFileSelect
            }}
            formControlProps={{
              fullWidth: true,
              required: false,
            }}
            />
          </CardBody>
        </Card>
    );
  } else if (item.type === 'AUDIO') {
    return (
      <Card>
        <CardBody>
        Add audio
        <CustomInput
          id="media"
          labelText="Add audio"
          inputProps={{
            name:"media",
            type:"file",
            onChange: handleFileSelect
          }}
          formControlProps={{
            fullWidth: true,
            required: false,
          }}
          />
        </CardBody>
      </Card>
    );
  } else {
    return (
      <CustomInput
        labelText="Add the details of your memory"
        id="0"
        inputProps={{
          name:"body",
          type:"text",
          onChange: onChangeText
        }}
        formControlProps={{
          fullWidth: true,
          required: true
        }}
      />
    );
  }
}

export default class AddNewMemory extends Component {
  constructor(props) {
    super(props);

    this.state = {
      memory: {
        headline: "",
        endDateDD: 0,
        endDateHH: 0,
        endDateMM: 0,
        endDateYYYY: 0,
        startDateDD: 0,
        startDateHH: 0,
        startDateMM: 0,
        startDateYYYY: 0,
        listOfItems: [
          {
            body: "",
            type: "",
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
        yearRange: 0
      },
      selectedFile: null,
      progress: 0,
    };

    this.handleAddNewLocation = this.handleAddNewLocation.bind(this);
    this.handleAddNewPhoto = this.handleAddNewPhoto.bind(this);
    this.handleAddNewVideo = this.handleAddNewVideo.bind(this);
    this.handleAddNewAudio = this.handleAddNewAudio.bind(this);
    this.handleAddNewText = this.handleAddNewText.bind(this);
  }

  handleAddNewMemory = event => {
    event.preventDefault();
    MemoryRepository.createMemory(this.state.memory)
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });
      // TODO: Bunu res in icine tasi.
      // window.location.reload();
      // const { history } = this.props;
      // history.push("/show-memory");

      console.log(this.state.memory);
  }

  handleAddNewLocation() {
    this.setState({
      listOfLocations: [
        ...this.state.memory.listOfLocations,
        {
          "latitude": 0,
          "locationName": "",
          "longitude": 0
        }
      ]
    });
  }

  handleAddNewPhoto(res) {
    this.setState({
      memory: {
        ...this.state.memory,
        listOfItems: [
          ...this.state.memory.listOfItems,
          {
            "body": "",
            "id": 0,
            "type": "PHOTO",
            "url": res
          }
        ]
      }
    });
  }
  handleAddNewVideo() {
    this.setState({
      memory: {
        ...this.state.memory,
        listOfItems: [
          ...this.state.memory.listOfItems,
          {
            "body": "",
            "id": 0,
            "type": "VIDEO",
            "url": null
          }
        ]
      }
    });
  }
  handleAddNewAudio() {
    this.setState({
      memory: {
        ...this.state.memory,
        listOfItems: [
          ...this.state.memory.listOfItems,
          {
            "body": "",
            "id": 0,
            "type": "AUDIO",
            "url": null
          }
        ]
      }
    });
  }
  handleAddNewText() {
    this.setState({memory: {
      ...this.state.memory,
      listOfItems: [
        ...this.state.memory.listOfItems,
        {
          body: "",
          type: "TEXT",
          url: ""
        }
      ]
    }});
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
        .then(res => this.handleAddNewPhoto(res)); //here here
    }
  }

render() {
console.log(this.state.memory.listOfItems);
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
              <GridItem xs={10} sm={10} md={10}>
                <CustomInput
                  labelText="Title"
                  id="headline"
                  value={this.state.memory.headline}
                  inputProps={{
                    name:"headline",
                    type:"text",
                    onChange: event => this.setState({memory: {
                      ...this.state.memory,
                      headline: event.target.value
                    }})
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: true
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>

              <GridItem xs={10} sm={10} md={4}>
                <Card>
                  <CardBody>
                      Add photo
                      <CustomInput
                        labelText="Add photo"
                        id="1"
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
                  </CardBody>
                </Card>
              </GridItem>

              <GridItem xs={10} sm={10} md={4}>
                <Card>
                  <CardBody>
                    Add video
                    <CustomInput
                      labelText="Add video"
                      id="2"
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
                    </CardBody>
                  </Card>
              </GridItem>

              <GridItem xs={10} sm={10} md={4}>
                <Card>
                  <CardBody>
                  Add audio
                  <CustomInput
                    id="3"
                    labelText="Add audio"
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
                  </CardBody>
                </Card>
              </GridItem>

              <GridItem xs={10} sm={10} md={10}>
                {this.state.memory.listOfItems.map( (item, i) => (

                  <NewMedia item={item} handleFileSelect={this.handleFileSelect} onChangeText={
                    (event, index) => {
                      const newListOfItems = this.state.memory.listOfItems;
                      newListOfItems[i].body = event.target.value;

                      this.setState({
                        memory: {
                          ...this.state.memory,
                          listOfItems: newListOfItems,
                        }
                      });
                  }} />
                ))}
              </GridItem>

              <GridItem xs={10} sm={10} md={4}>
                <Button
                  onClick={this.handleAddNewPhoto}
                  color="transparent"
                > Add more photo
                </Button>
              </GridItem>
              <GridItem xs={10} sm={10} md={4}>
                <Button
                  onClick={this.handleAddNewVideo}
                  color="transparent"
                > Add more video
                </Button>
              </GridItem>
              <GridItem xs={10} sm={10} md={4}>
                <Button
                  onClick={this.handleAddNewAudio}
                  color="transparent"
                > Add more audio
                </Button>
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={3}>
                <Button
                  onClick={this.handleAddNewText}
                  color="transparent"
                > Add more text
                </Button>
              </GridItem>

              <GridItem xs={10} sm={10} md={3}>
                <Button
                  onClick={this.handleFileUpload}
                  round
                  color="info"
                > Upload
                </Button>
                <ProgressBar now={this.progress} />
              </GridItem>
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
              {this.state.memory.listOfLocations.map( (location, i) => (
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
              <GridItem xs={10} sm={10} md={10}>
                <CustomInput
                  labelText="Tags"
                  id="listOfTags"
                  value={this.state.memory.listOfTags}
                  inputProps={{
                    name:"listOfTags",
                    type:"text",
                    onChange: event => this.setState({memory: {
                      ...this.state.memory,
                      listOfTags: event.target.value
                    }})
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={3}>
                <CustomInput
                  labelText="Year (YYYY)"
                  id="start_date_yyyy"
                  value={this.state.memory.startDateYYYY}
                  inputProps={{
                    name:"startDateYYYY",
                    type:"text",
                    onChange: event => this.setState({memory: {
                      ...this.state.memory,
                      startDateYYYY: event.target.value
                    }})
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
                  value={this.state.memory.startDateMM}
                  inputProps={{
                    name:"startDateMM",
                    type:"text",
                    onChange: event => this.setState({memory: {
                      ...this.state.memory,
                      startDateMM: event.target.value
                    }})
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
                  value={this.state.memory.startDateDD}
                  inputProps={{
                    name:"startDateDD",
                    type:"text",
                    onChange: event => this.setState({memory: {
                      ...this.state.memory,
                      startDateDD: event.target.value
                    }})
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
              <GridItem xs={10} sm={10} md={3}>
                <CustomInput
                  labelText="Hour (0-23)"
                  id="start_date_hh"
                  value={this.state.memory.startDateHH}
                  inputProps={{
                    name:"startDateHH",
                    type:"text",
                    onChange: event => this.setState({memory: {
                      ...this.state.memory,
                      startDateHH: event.target.value
                    }})
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={3}>
                <CustomInput
                  labelText="Year (YYYY)"
                  id="end_date_yyyy"
                  value={this.state.memory.endDateYYYY}
                  inputProps={{
                    name:"endDateYYYY",
                    type:"text",
                    onChange: event => this.setState({memory: {
                      ...this.state.memory,
                      endDateYYYY: event.target.value
                    }})
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
                  value={this.state.memory.endtDateMM}
                  inputProps={{
                    name:"endtDateMM",
                    type:"text",
                    onChange: event => this.setState({memory: {
                      ...this.state.memory,
                      endtDateMM: event.target.value
                    }})
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
                  value={this.state.memory.endDateDD}
                  inputProps={{
                    name:"endDateDD",
                    type:"text",
                    onChange: event => this.setState({memory: {
                      ...this.state.memory,
                      endDateDD: event.target.value
                    }})
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
              <GridItem xs={10} sm={10} md={3}>
                <CustomInput
                  labelText="Hour (0-23)"
                  id="end_date_hh"
                  value={this.state.memory.endDateHH}
                  inputProps={{
                    name:"endDateHH",
                    type:"text",
                    onChange: event => this.setState({memory: {
                      ...this.state.memory,
                      endDateHH: event.target.value
                    }})
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
            </GridContainer>

            <GridContainer>
              <GridItem xs={10} sm={10} md={3}>
                <CustomInput
                  labelText="10-year range"
                  id="start_date_yyyy"
                  value={this.state.memory.startDateYYYY}
                  inputProps={{
                    name:"startDateYYYY",
                    type:"text",
                    onChange: event => this.setState({memory: {
                      ...this.state.memory,
                      startDateYYYY: event.target.value,
                      yearRange: "10"
                    }})
                  }}
                  formControlProps={{
                    fullWidth: true,
                    required: false
                  }}
                />
              </GridItem>
              <GridItem xs={10} sm={10} md={3}>
                <CustomInput
                  labelText="100-year range"
                  id="start_date_yyyy"
                  value={this.state.memory.startDateYYYY}
                  inputProps={{
                    name:"startDateYYYY",
                    type:"text",
                    onChange: event => this.setState({memory: {
                      ...this.state.memory,
                      startDateYYYY: event.target.value,
                      yearRange: "100"
                    }})
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

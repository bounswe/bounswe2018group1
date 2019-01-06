import Radio from '@material-ui/core/Radio';
import React from "react";

import CustomInput from "components/CustomInput/CustomInput.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import Button from "components/CustomButtons/Button.jsx";

import SearchRepository from '../../api_calls/search.js';

export default class Search extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      queryType: "",
      queryString: "",

      endDateDD: 0,
      endDateHH: 0,
      endDateMM: 0,
      endDateYYYY: 0,
      listOfTags: [
        { text: "" }
      ],
      location: {
        latitude: 0,
        locationName: "",
        longitude: 0
      },
      startDateDD: 0,
      startDateHH: 0,
      startDateMM: 0,
      startDateYYYY: 0,
      text: ""
    };

    //this.handleWUTWUTWUT = this.handleWUTWUTWUT.bind(this);

  }

  handleChange = event => {
    this.setState({ queryType: event.target.value });
    console.log(this.state);
  };

  handleSearch = event => {
    event.preventDefault();
    console.log(this.state); //Remove after development is complete.
/*
    if(this.state.queryType === 'Text') {
      this.setState({ text: queryString })
    }
    else if (this.state.queryType === 'Location') {
      this.setState({ location.locationName: queryString })
    }
    else if (this.state.queryType === 'Date') {
      this.setState({  })
    }
    else if (this.state.queryType === 'User') {
      this.setState({  })
    }
    else if (this.state.queryType === 'Tag') {
      this.setState({  })
    }
    else {
      //defaults to text
      this.setState({ text: queryString })
    }

    SearchRepository.search(endDateDD, endDateHH, endDateMM, endDateYYYY, listOfTags, location, startDateDD, startDateMM, startDateHH, startDateYYYY, text)
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });

      */
  }


  render() {
    return(
      <GridContainer>
        <GridItem xs={10} sm={10} md={4}>
          <CustomInput
            id="queryString"
            inputProps={{
              name:"queryString",
              type:"text",
              onChange: event => this.setState({ queryString: event.target.value })
            }}
            formControlProps={{
              fullWidth: true,
              required: false,
            }}
          />
        </GridItem>

        <Button onClick={this.handleSearch} round color="info"> Search </Button>

        <Radio
          checked={this.state.queryType === 'Text'}
          onChange={this.handleChange}
          value="Text"
          name="radio-button"
          aria-label="Text"
        />
        <Radio
          checked={this.state.queryType === 'Location'}
          onChange={this.handleChange}
          value="Location"
          name="radio-button"
          aria-label="Location"
        />
        <Radio
          checked={this.state.queryType === 'Date'}
          onChange={this.handleChange}
          value="Date"
          name="radio-button"
          aria-label="Date"
        />
        <Radio
          checked={this.state.queryType === 'User'}
          onChange={this.handleChange}
          value="User"
          name="radio-button"
          aria-label="User"
        />
        <Radio
          checked={this.state.queryType === 'Tag'}
          onChange={this.handleChange}
          value="Tag"
          name="radio-button"
          aria-label="Tag"
        />
      </GridContainer>

      )
  }

}

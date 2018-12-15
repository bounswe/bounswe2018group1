import SearchBar from 'material-ui-search-bar'
import Radio from '@material-ui/core/Radio';
import React from "react";

import SearchRepository from '../../api_calls/search.js';

export default class Search extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      queryType: "",
      queryString: ""
    };

    //this.handleWUTWUTWUT = this.handleWUTWUTWUT.bind(this);

  }

  handleChange = event => {
    this.setState({ queryType: event.target.value });
  };

  handleSearch = event => {
    event.preventDefault();
    console.log(this.state); //Remove after development is complete.

    SearchRepository.search(this.state.queryType, this.state.queryString)
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });
  }


  render() {
    return(
      <div>
        <SearchBar
          onChange={event => this.setState({ queryString: event.target.value })}
          onRequestSearch={this.handleSearch}
          style={{
            margin: '0 auto',
            maxWidth: 800
          }}
        />

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
      </div>
      )
  }

}

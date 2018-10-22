import React, { Component } from 'react';
import Memory from '../../containers/Memory.js';
import { createMemory } from '../../actions/createMemory';

export default class Create extends Component {

    handleSubmit(data) {
        console.log('form submission data', data);
    }

    render() {
        return (
            <div>
              <Memory onSubmit={this.handleSubmit}></Memory>
            </div>
        );
    }
}

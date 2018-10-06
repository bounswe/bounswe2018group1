import React, { Component } from 'react';
import Route from "route"
import Login from "./containers/Login";

class Route extends Component {
  render () {
    return (<Route path="/login" exact component={Login} />)
  }
}

export default Route;

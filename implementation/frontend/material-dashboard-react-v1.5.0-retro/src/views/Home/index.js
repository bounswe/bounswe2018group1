import React, { Component } from "react";
import { Switch, Route } from "react-router";
import MemoryFeed from "./MemoryFeed/MemoryFeed";
import ShowMemory from "./ShowMemory/ShowMemory";

class Home extends Component {
  render() {
    return (
      <div>
        <Switch>
          <Route exact path="/home" component={MemoryFeed} />
          <Route path="/home/show-memory" render={({ location }) => <ShowMemory id={location.state.id} />} />
        </Switch>
      </div>
    );
  }
}
export default Home;

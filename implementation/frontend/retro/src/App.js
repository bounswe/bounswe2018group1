import React, { Component } from 'react';
import './App.css';
import ReactDOM from 'react-dom';
import Login from './containers/Login/Login.js';

class App extends Component {

  render() {
    return (
      ReactDOM.render(<Login />, document.getElementById('root'))

      // TODO: if login then go home page with login conditions.
    );
  }
}

export default App;

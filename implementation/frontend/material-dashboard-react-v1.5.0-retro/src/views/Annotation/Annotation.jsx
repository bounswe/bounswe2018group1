import React from 'react';
// @material-ui/core
import withStyles from "@material-ui/core/styles/withStyles";
// core components
import Button from "components/CustomButtons/Button.jsx";
import CustomInput from "components/CustomInput/CustomInput.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";

import AnnotationRepository from '../../api_calls/annotation_calls.js';

export default class Annotation extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      annotation: ""
    };
    this.handleAddNewAnnotation = this.handleAddNewAnnotation.bind(this);
  }

  handleAddNewAnnotation = event => {
    event.preventDefault();
    console.log(this.state); //Remove after development is complete.

    AnnotationRepository.postAnnotation(this.state.annotation, this.props.memory.id)
      .then(res => {
        console.log(res);
        window.location.reload();
      })
      .catch(err => {
        console.log(err);
      });
  }

  render() {

   return (
     <GridContainer>
       <GridItem xs={10} sm={10} md={10}>
         <CustomInput
           labelText="Write a annotation"
           id="annotation"
           inputProps={{
             name:"annotation",
             type:"text",
             onChange: event => this.setState({ annotation: event.target.value })
           }}
           formControlProps={{
             fullWidth: true,
             required: false
           }}
         />
       </GridItem>
       <GridItem xs={10} sm={10} md={2}>
         <Button onClick={this.handleAddNewAnnotation} color="info">Send Annotation</Button>
       </GridItem>
      </GridContainer>
     );
   }
}

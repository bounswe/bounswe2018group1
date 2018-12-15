import React from 'react';
// @material-ui/core
import withStyles from "@material-ui/core/styles/withStyles";
// core components
import Button from "components/CustomButtons/Button.jsx";
import CustomInput from "components/CustomInput/CustomInput.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";

import Like_CommentRepository from '../../api_calls/like_comment.js';

export default class Comment extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      text: "",
      userId: 0,
      memoryId: 0,
    };

    //this.handleAddNewLocation = this.handleAddNewLocation.bind(this);

  }

  handleAddNewComment = event => {
    event.preventDefault();
    console.log(this.state); //Remove after development is complete.

    Like_CommentRepository.comment(this.state.userId, this.state.memoryId, this.state.text)
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });
  }

  render() {

   return (
     <GridContainer>
       <GridItem xs={10} sm={10} md={8}>
         <CustomInput
           placeholder="Write a comment"
           id="comment"
           inputProps={{
             name:"comment",
             type:"text",
             onChange: event => this.setState({ content: event.target.value })
           }}
           formControlProps={{
             fullWidth: true,
             required: false
           }}
         />
       </GridItem>
       <Button onClick={this.handleAddNewComment} color="info"> </Button>
      </GridContainer>

     );
   }

}

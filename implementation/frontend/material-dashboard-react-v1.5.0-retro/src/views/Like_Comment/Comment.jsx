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
      comment: ""
    };
    this.handleAddNewComment = this.handleAddNewComment.bind(this);
  }

  handleAddNewComment = event => {
    event.preventDefault();
    console.log(this.state); //Remove after development is complete.

    Like_CommentRepository.comment(this.state.comment, this.props.memory.id)
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
           labelText="Write a comment"
           id="comment"
           inputProps={{
             name:"comment",
             type:"text",
             onChange: event => this.setState({ comment: event.target.value })
           }}
           formControlProps={{
             fullWidth: true,
             required: false
           }}
         />
       </GridItem>
       <GridItem xs={10} sm={10} md={2}>
         <Button onClick={this.handleAddNewComment} color="info">Send Comment</Button>
       </GridItem>
      </GridContainer>
     );
   }
}

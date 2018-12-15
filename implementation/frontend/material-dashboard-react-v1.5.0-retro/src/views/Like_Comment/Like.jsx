import React from 'react';
// @material-ui/core
import withStyles from "@material-ui/core/styles/withStyles";
// core components
import Button from "components/CustomButtons/Button.jsx";

import Like_CommentRepository from '../../api_calls/like_comment.js';

export default class Like extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      likes: 0,
      liked: false,
      owner: "",
      memoryId: 0
    };

    this.handleLike = this.handleLike.bind(this);
    this.handleUnlike = this.handleUnlike.bind(this);
  }

  handleLike() {
    console.log(this.state); //Remove after development is complete.

    Like_CommentRepository.like(this.state.owner)
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });
  }

  handleUnlike() {
    console.log(this.state); //Remove after development is complete.

    Like_CommentRepository.unlike(this.state.owner)
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });
  }

  updateLikes = event => {
    event.preventDefault();

    if(!this.state.liked) {
      this.setState((prevState, props) => {
        return {
          likes: prevState.likes + 1,
          liked: true
        };
      });
      this.handleLike();

    } else {

      this.setState((prevState, props) => {
        return {
          likes: prevState.likes - 1,
          liked: false
        };
      });
      this.handleUnlike();

    }
  }

  render(){
    const pic = this.state.liked ? <i class="fas fa-heart"></i> : <i class="far fa-heart"></i>

    return(
      <div>
         <Button onClick={this.updateLikes} color="info"> {pic} {this.state.likes} likes </Button>
      </div>
    );
  }
}

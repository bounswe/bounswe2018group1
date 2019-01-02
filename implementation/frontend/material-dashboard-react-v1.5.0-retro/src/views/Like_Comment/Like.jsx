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
      likes: [],
      liked: false
    };

    this.handleLikeCounts = this.handleLikeCounts.bind(this);
    this.handleLike = this.handleLike.bind(this);
    this.handleUnlike = this.handleUnlike.bind(this);

  }

  handleLikeCounts() {
      this.setState({ likes: this.props.memory.likedUsers });
  }

  handleLike() {

    Like_CommentRepository.like(this.props.memory.id)
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });
  }

  handleUnlike() {

    Like_CommentRepository.unlike(this.props.memory.id)
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });
  }

  updateLikes = event => {
    event.preventDefault();
    this.handleLikeCounts();

    if(!this.state.liked) {
      this.setState((props) => {
        return {
          likes: this.state.likes + 1,
          liked: true
        };
      });
      this.handleLike();

    } else {

      this.setState((props) => {
        return {
          likes: this.state.likes - 1,
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

import React from 'react';
import classNames from "classnames";

import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import FormControl from "@material-ui/core/FormControl";
import customInputStyle from "assets/jss/material-dashboard-react/components/customInputStyle.jsx";

function DatePickers(props) {
  const {
    classes,
    formControlProps,
    labelText,
    error,
    success
  } = props;

  const underlineClasses = classNames({
    [classes.underlineError]: error,
    [classes.underlineSuccess]: success && !error,
    [classes.underline]: true
  });
  const marginTop = classNames({
    [classes.marginTop]: labelText === undefined
  });

  const textFieldClasses = classNames({
    root: marginTop,
    disabled: classes.disabled,
    underline: underlineClasses,
    [classes.textField]: true
  });

  return (
    <FormControl {...formControlProps}>
      <TextField

        id="date"
        label={ labelText }
        type="date"
        defaultValue="2018-10-30"
        className={textFieldClasses}
        InputLabelProps={{
          shrink: true,
        }}
      />
    </FormControl>
  );
}

DatePickers.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(customInputStyle)(DatePickers);

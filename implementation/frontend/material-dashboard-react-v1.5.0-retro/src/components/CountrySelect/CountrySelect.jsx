import React from 'react';
import classNames from "classnames";

import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';

import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
// @material-ui/icons
import Clear from "@material-ui/icons/Clear";
import Check from "@material-ui/icons/Check";
import customInputStyle from "assets/jss/material-dashboard-react/components/customInputStyle.jsx";

import GridItem from "components/Grid/GridItem.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import OutlinedInput from '@material-ui/core/OutlinedInput';
import FilledInput from '@material-ui/core/FilledInput';
import FormHelperText from '@material-ui/core/FormHelperText';
import Select from '@material-ui/core/Select';
import NativeSelects from '@material-ui/core/NativeSelect';

import {
  CountryDropdown,
  RegionDropdown,
  CountryRegionData
} from "react-country-region-selector-material-ui-new";

// note that you can also export the source data via CountryRegionData. It's in a deliberately concise format to
// keep file size down

class CountrySelect extends React.Component {
  constructor(props) {
    super(props);
    this.state = { country: "", region: "" };
  }

  selectCountry(val) {
    this.setState({ country: val });
  }

  selectRegion(val) {
    this.setState({ region: val });
  }

  render() {
    const { country, region } = this.state;

    const {
      classes,
      formControlProps,
      labelText,
      id,
      labelProps,
      inputProps,
      error,
      success
    } = this.props;

    const labelClasses = classNames({
      [" " + classes.labelRootError]: error,
      [" " + classes.labelRootSuccess]: success && !error
    });
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
      <div>
        <GridContainer style={{ width: "200%" }}>
          <GridItem xs={12} sm={12} md={6}>
            <FormControl {...formControlProps}
              className={formControlProps.className + " " + classes.formControl}
            >
              <InputLabel
                className={classes.labelRoot + labelClasses}
                htmlFor={id}
                {...labelProps}
              >
                Country
              </InputLabel>

              {/*
                classes={{
                  root: marginTop,
                  disabled: classes.disabled,
                  underline: underlineClasses
                }}
              */}
              <CountryDropdown
                id="country"
                value={country}
                className={textFieldClasses}
                onChange={val => this.selectCountry(val)}
              />
              {error ? (
                <Clear className={classes.feedback + " " + classes.labelRootError} />
              ) : success ? (
                <Check className={classes.feedback + " " + classes.labelRootSuccess} />
              ) : null}
            </FormControl>
          </GridItem>
          <GridItem xs={12} sm={12} md={6}>
            <FormControl {...formControlProps}
              className={formControlProps.className + " " + classes.formControl}
            >
              <InputLabel
                className={classes.labelRoot + labelClasses}
                htmlFor={id}
                {...labelProps}
              >
                Region
              </InputLabel>

              {/*
                classes={{
                  root: marginTop,
                  disabled: classes.disabled,
                  underline: underlineClasses
                }}
              */}
              <RegionDropdown
                country={country}
                id="region"
                {...inputProps}
                value={region}
                defaultOptionLabel=""
                className={textFieldClasses}
                onChange={val => this.selectRegion(val)}
              />
              {error ? (
                <Clear className={classes.feedback + " " + classes.labelRootError} />
              ) : success ? (
                <Check className={classes.feedback + " " + classes.labelRootSuccess} />
              ) : null}
            </FormControl>
          </GridItem>
        </GridContainer>
      </div>
    );
  }
}

CountrySelect.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(customInputStyle)(CountrySelect);

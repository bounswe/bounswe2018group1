import React, {Component} from 'react';
import {
    Alert,
    StyleSheet,
    Text,
    View,
    TextInput,
    ViewPropTypes
} from 'react-native';
import PropTypes from 'prop-types';

class TextField extends Component {
    static propTypes = {
        myStyle: ViewPropTypes.style,
        fieldValue: PropTypes.string,
        myTextContentType: PropTypes.string,
        mySecureTextEntry: PropTypes.bool,
    };
    static defaultProps = {
        myStyle: {},
        mySecureTextEntry: false
    };

    constructor(props) {
        super(props);
        this.state = {text: ''}
    }

    getText() {
        return (this.state.text);
    }

    render() {
        return (
            <View style={this.props.myStyle}>
                <Text style={styles.formFieldName}>{this.props.fieldValue}</Text>
                <TextInput
                    style={styles.formText}
                    placeholder='Write Here.'
                    textContentType={this.props.myTextContentType}
                    secureTextEntry={this.props.mySecureTextEntry}
                    onChangeText={(text) => this.setState({text})}
                />
            </View>
        );
    }
}

class RegisterForm extends Component {
    static propTypes = {
        myStyle: ViewPropTypes.style,
    };
    getValue() {
        return (
            {
                'name': this.refs.nameRef.getText(),
                'email': this.refs.emailRef.getText(),
                'password': this.refs.passRef.getText(),
                'confirmPassword': this.refs.confPassRef.getText(),
            }
        );
    }

    render() {
        return (
            <View style={this.props.myStyle}>
                <TextField myStyle={styles.mainForm}
                           fieldValue='Name'
                           myTextContentType='name'
                           ref='nameRef'
                />
                <TextField myStyle={styles.mainForm}
                           fieldValue='Email'
                           myTextContentType='emailAddress'
                           ref='emailRef'
                />
                <TextField myStyle={styles.mainForm}
                           fieldValue='Password'
                           myTextContentType='password'
                           mySecureTextEntry={true}
                           ref='passRef'
                />
                <TextField myStyle={styles.mainForm}
                           fieldValue='Confirm Password'
                           myTextContentType='password'
                           mySecureTextEntry={true}
                           ref='confPassRef'
                />
            </View>
        );
    }
}

const styles = StyleSheet.create({
    formFieldName: {
        margin: 5,
        fontSize: 12,
        textAlign: 'left',
        color: 'black',
    },
    formText: {
        height: 40,
        fontSize: 16,
    },
    mainForm: {},
});

module.exports = {
    TextField: TextField,
    RegisterForm: RegisterForm,
};
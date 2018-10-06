/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, {Component} from 'react';
import {
    Alert,
    TouchableOpacity,
    Button,
    StyleSheet,
    Text,
    ScrollView,
    Keyboard
} from 'react-native';
import {RegisterForm} from './RegisterForm';



type Props = {};
export default class App extends Component<Props> {
    constructor(props) {
        super(props);
        this.state = {
            confirmPasswordError: false,
            confirmPasswordMsg: 'This field is required!'
        };
    }

    handleSubmit = () => {
        Keyboard.dismiss();
        const value = this.refs.formRef.getValue();
        Alert.alert('handleSubmit', JSON.stringify(value));
    }

    render() {
        return (
            <ScrollView contentContainerStyle={styles.container}>
                <Text style={styles.title}>Register</Text>
                <RegisterForm ref='formRef'
                              myStyle={styles.mainForm}
                />
                <TouchableOpacity onPress={this.handleSubmit}
                                  style={styles.button}>
                    <Text style={styles.buttonText}>REGISTER</Text>
                </TouchableOpacity>
            </ScrollView>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flexGrow: 1,
        flexDirection: 'column',
        alignItems: 'center',
        backgroundColor: '#E0B0B0',
        paddingLeft: 10,
        paddingRight: 10,
    },
    title: {
        fontSize: 24,
        textAlign: 'center',
        margin: 15,
        textDecorationLine: 'underline',
        color: 'black',
    },
    mainForm: {
        marginBottom: 15,
        alignSelf: 'stretch',
    },
    button: {
        backgroundColor: '#B04038',
        padding: 10,
        width: '50%',
        alignItems: 'center',
    },
    buttonText: {
        color: '#FFFFFF',
        fontWeight: 'bold',
    },
});

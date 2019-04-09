import React, { Component } from "react";
import { geolocated } from "react-geolocated";

class Weather extends Component {
  state = {
    geolocationAvail: false,
    weatherRetrieved: false
  };

  // Including "https://cors-anywhere.herokuapp.com/" since without, openweathermap throws the CORS error.
  baseURL =
    "https://cors-anywhere.herokuapp.com/http://api.openweathermap.org/data/2.5/weather";
  apiKey = "6a4cead944b1ffa85d204424cb503722";

  getWeatherData = () => {
    var weatherAPIURL =
      this.baseURL +
      "?lat=" +
      this.props.coords.latitude +
      "&lon=" +
      this.props.coords.longitude +
      "&APPID=" +
      this.apiKey;

    fetch(weatherAPIURL, {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    })
      .then(result => result.json())
      .then(result => {
        console.log("Weather API result: " + JSON.stringify(result));
        this.setState({
          weatherRetrieved: true,
          city: result.name,
          country: result.sys.country,
          temperature: Number((result.main.temp - 273.15).toFixed(2)) // Returns Kelvin.
        });
      });
    //console.log(this.state);
  };

  getResult = () => {
    if (
      !this.props.isGeolocationAvailable ||
      !this.props.isGeolocationEnabled
    ) {
      return <div>Geolocation data unavailable</div>;

      // This needs to be updated since there's still a delay between this.props.coords
      // being available, and the actual weather data being available from the API.
    } else if (!this.props.coords) {
      return <div>Getting location data...</div>;
    }

    if (!this.state.weatherRetrieved) {
      this.getWeatherData();
    }
    return (
      <div>
        Your current location: {this.state.city}, {this.state.country}
        <br />
        Your current temperature: {this.state.temperature}°C
      </div>
    );
  };

  render() {
    if (!this.state.weatherRetrieved) {
      //this.getWeatherData();
    }
    return (
      <div className="container">
        <h2>Weather</h2>
        {this.getResult()}
      </div>
    );
  }
}

export default geolocated({
  positionOptions: {
    enableHighAccuracy: false
  },
  userDecisionTimeout: 5000
})(Weather);

/*import React from 'react';
import {geolocated} from 'react-geolocated';
 
class Demo extends React.Component {
  render() {
    
  }
}*/

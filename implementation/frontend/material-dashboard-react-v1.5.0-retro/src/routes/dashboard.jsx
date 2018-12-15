import Cookies from "js-cookie";

// @material-ui/icons
import Home from "@material-ui/icons/Home";
import Person from "@material-ui/icons/Person";
import AddCircle from "@material-ui/icons/AddCircle";
import Memory from "@material-ui/icons/Memory";
import HowToReg from "@material-ui/icons/HowToReg";
import LocationOn from "@material-ui/icons/LocationOn";

import UserProfile from "views/UserProfile/UserProfile.jsx";
import TableList from "views/TableList/TableList.jsx";
import Typography from "views/Typography/Typography.jsx";
import Icons from "views/Icons/Icons.jsx";
import Maps from "views/Maps/Maps.jsx";
import NotificationsPage from "views/Notifications/Notifications.jsx";
import AddNewMemory from "views/AddNewMemory/AddNewMemory.jsx";
import HomePage from "views/Home";
import Login from "views/Login/Login.jsx";

const userToken = Cookies.get("JSESSIONID");

const dashboardRoutes = [
  {
    path: "/home",
    sidebarName: "Home",
    navbarName: "Home",
    icon: Home,
    component: HomePage
  },

  {
    path: "/maps",
    sidebarName: "Maps",
    navbarName: "Map",
    icon: LocationOn,
    component: Maps
  },
  userToken
    ? {
        path: "/user",
        sidebarName: "User Profile",
        navbarName: "Profile",
        icon: Person,
        component: UserProfile
      }
    : null,
  userToken
    ? {
        path: "/add-new-memory",
        sidebarName: "Add new Memory",
        navbarName: "Add new Memory",
        icon: AddCircle,
        component: AddNewMemory
      }
    : null,
  userToken
    ? null
    : {
        path: "/login",
        sidebarName: "Login",
        navbarName: "Login",
        icon: HowToReg,
        component: Login
      },
  { redirect: true, path: "/", to: "/home", navbarName: "Redirect" }
];

export default dashboardRoutes.filter(Boolean);

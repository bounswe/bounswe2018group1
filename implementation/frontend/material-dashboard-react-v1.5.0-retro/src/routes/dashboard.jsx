import Cookies from "js-cookie";
// @material-ui/icons
import Home from "@material-ui/icons/Home";
import Person from "@material-ui/icons/Person";
// import ContentPaste from "@material-ui/icons/ContentPaste";
import AddCircle from "@material-ui/icons/AddCircle";
import Memory from "@material-ui/icons/Memory";
import HowToReg from "@material-ui/icons/HowToReg";
import LibraryBooks from "@material-ui/icons/LibraryBooks";
import BubbleChart from "@material-ui/icons/BubbleChart";
import LocationOn from "@material-ui/icons/LocationOn";
import Notifications from "@material-ui/icons/Notifications";
import Unarchive from "@material-ui/icons/Unarchive";
// core components/views
import DashboardPage from "views/Dashboard/Dashboard.jsx";
import UserProfile from "views/UserProfile/UserProfile.jsx";
import TableList from "views/TableList/TableList.jsx";
import Typography from "views/Typography/Typography.jsx";
import Icons from "views/Icons/Icons.jsx";
import Maps from "views/Maps/Maps.jsx";
import NotificationsPage from "views/Notifications/Notifications.jsx";
import AddNewMemory from "views/AddNewMemory/AddNewMemory.jsx";
import ShowMemory from "views/ShowMemory/ShowMemory.jsx";
import Login from "views/Login/Login.jsx";

const userToken = Cookies.get("retroToken");

const dashboardRoutes = [
  {
    path: "/home",
    sidebarName: "Home",
    navbarName: "Home",
    icon: Home,
    component: ShowMemory
  },
   {
     path: "/user",
     sidebarName: "User Profile",
     navbarName: "Profile",
     icon: Person,
     component: UserProfile
   },
  // {
  //   path: "/table",
  //   sidebarName: "Table List",
  //   navbarName: "Table List",
  //   icon: "content_paste",
  //   component: TableList
  // },
  // {
  //   path: "/typography",
  //   sidebarName: "Typography",
  //   navbarName: "Typography",
  //   icon: LibraryBooks,
  //   component: Typography
  // },
  // {
  //   path: "/icons",
  //   sidebarName: "Icons",
  //   navbarName: "Icons",
  //   icon: BubbleChart,
  //   component: Icons
  // },
  // {
  //   path: "/maps",
  //   sidebarName: "Maps",
  //   navbarName: "Map",
  //   icon: LocationOn,
  //   component: Maps
  // },
  // {
  //   path: "/notifications",
  //   sidebarName: "Notifications",
  //   navbarName: "Notifications",
  //   icon: Notifications,
  //   component: NotificationsPage
  // },
  userToken ? {
    path: "/add-new-memory",
    sidebarName: "Add new Memory",
    navbarName: "Add new Memory",
    icon: AddCircle,
    component: AddNewMemory
  } : null,
  // {
  //   path: "/home-page",
  //   sidebarName: "Home",
  //   navbarName: "Home",
  //   icon: Home,
  //   component: ShowMemory
  // },
  {
    path: "/login",
    sidebarName: "Login",
    navbarName: "Login",
    icon: HowToReg,
    component: Login
  },
  { redirect: true, path: "/", to: "/home", navbarName: "Redirect" }
];

export default dashboardRoutes.filter(Boolean);

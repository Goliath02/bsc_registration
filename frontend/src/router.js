import { createRouter, createWebHistory } from "vue-router";

import MainPage from "@/components/Pages/MainPage.vue";
import DatenschutzPage from "@/components/Pages/DatenschutzPage.vue";
import DefaultRegistration from "@/components/Pages/DefaultRegistration.vue";
import FinanzialRegistration from "@/components/Pages/FinanzialRegistration.vue";
import ConfirmationPage from "@/components/Pages/ConfirmationPage.vue";
import RequestSuccessPage from "@/components/Pages/RequestSuccessPage.vue";
import NotSwimmerMainPage from "@/components/Pages/NotSwimmerMainPage.vue";
import NswRegistration from "@/components/Pages/NswRegistration.vue";
import NswFinanzialRegistration from "@/components/Pages/NswFinanzialRegistration.vue";
import NswConfirmationPage from "@/components/Pages/NswConfirmationPage.vue";
import LoginPage from "@/components/Login/LoginPage.vue";
import CourseManager from "@/components/CourseManager/CourseManager.vue";
import axios from "axios";
import Dashboardpage from "@/AdminPannel/Pages/Dashboardpage.vue";
import ProfilePage from "@/AdminPannel/Pages/ProfilePage.vue";
import CoursePage from "@/AdminPannel/Pages/CoursePage.vue";
import TrainerPage from "@/AdminPannel/Pages/TrainerPage.vue";
import SettingsPage from "@/AdminPannel/Pages/SettingsPage.vue";
import MembersPage from "@/AdminPannel/Pages/MembersPage.vue";
import TrainingPlacesPage from "@/AdminPannel/Pages/TrainingPlacesPage.vue";

const routes = [
  {
    path: "/",
    component: MainPage,
    children: [
      { path: "", component: DefaultRegistration, name: "DefaultRegistration" },
      {
        path: "/kontodaten",
        component: FinanzialRegistration,
        name: "FinanzialRegistration",
      },
      {
        path: "/zusammenfassung",
        component: ConfirmationPage,
        name: "ConfirmationPage",
      },
    ],
  },
  { path: "/datenschutz", component: DatenschutzPage },
  { path: "/erfolg", component: RequestSuccessPage, name: "Erfolg" },
  {
    path: "/nichtschwimmer",
    component: NotSwimmerMainPage,
    name: "NotSwimmerMainPage",
    children: [
      { path: "", component: NswRegistration, name: "NswDefaultRegistration" },
      {
        path: "/nswKontodaten",
        component: NswFinanzialRegistration,
        name: "NswFinanzialRegistration",
      },
      {
        path: "/nwsZusammenfassung",
        component: NswConfirmationPage,
        name: "NswConfirmationPage",
      },
    ],
  },

  {
    path: "/login",
    component: LoginPage,
    name: "Login",
  },
  {
    path: "/courseManager",
    component: CourseManager,
    name: "CourseManager",
    meta: { requiresAuth: true },
    children: [
      { path: "/dashboard", component: Dashboardpage, name: "Dashboard" },
      { path: "/profile", component: ProfilePage, name: "Profile" },
      { path: "/courses", component: CoursePage, name: "Courses" },
      { path: "/members", component: MembersPage, name: "Members" },
      {
        path: "/places",
        component: TrainingPlacesPage,
        name: "TrainingPlaces",
      },
      { path: "/trainer", component: TrainerPage, name: "Trainer" },
      { path: "/settings", component: SettingsPage, name: "Settings" },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach(async (to, from, next) => {
  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);

  if (!requiresAuth) {
    return next();
  }

  try {
    // Anfrage an geschützten Endpoint – prüft Cookie
    await axios.get("http://localhost:8080/api/auth/authenticated", {
      withCredentials: true,
    });

    next(); // Zugriff erlaubt
  } catch (error) {
    console.warn("Nicht eingeloggt");
    next("/login"); // Weiterleitung zur Login-Seite
  }
});
export default router;

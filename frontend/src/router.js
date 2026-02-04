import { createRouter, createWebHistory } from "vue-router";

import MainPage from "@/components/Pages/MainPage.vue";
import DatenschutzPage from "@/components/Pages/DatenschutzPage.vue";
import DefaultRegistration from "@/components/Pages/DefaultRegistration.vue";
import FinanzialRegistration from "@/components/Pages/FinanzialRegistration.vue";
import ConfirmationPage from "@/components/Pages/ConfirmationPage.vue";
import RequestSuccessPage from "@/components/Pages/RequestSuccessPage.vue";
import axios from "axios";

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
    next("/"); // Weiterleitung zur Login-Seite
  }
});
export default router;

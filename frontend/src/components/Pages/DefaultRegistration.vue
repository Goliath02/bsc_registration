<script setup >
import FormHeader from "@/components/FormHeader.vue";
import AddMemberButton from "@/components/BasicRegistration/AddMemberButton.vue";
import ExtraPersonForm from "@/components/BasicRegistration/ExtraPersonForm.vue";
import { useRegistrationStore } from "@/stores/RegistrationStore.js";
import NachweisFeld from "@/components/BasicRegistration/NachweisFeld.vue";
import { Form } from "@primevue/forms";
import { computed, onMounted, ref } from "vue";
import axios from "axios";
import Button from "primevue/button";
import Popover from "primevue/popover";
import DatePicker from "primevue/datepicker";
import FloatLabel from "primevue/floatlabel";
import * as yup from "yup";
import { yupResolver } from "@primevue/forms/resolvers/yup";
import router from "@/router.js";
import { RegistrationType } from "@/stores/Registration.ts";
import { getTargetURL } from "@/apiClient.ts";

const resolver = yupResolver(
  yup.object().shape({
    type: yup.string().required("Kategorie wählen."),
    reason: yup.string().required("Abteilung wählen."),
    name: yup.string().required("Name wird benötigt."),
    surename: yup.string().required("Nachname wird benötigt."),
    birthday: yup.date().required("Geburtsdatum angeben."),
    gender: yup.string().required("Geschlecht angeben."),
    email: yup
      .string()
      .email("Bitte ein gültige Email angeben")
      .required("Email wird benötigt."),
    phone: yup.string().required("Telefonnummer wird benötigt."),
    street: yup.string().required("Straße wird benötigt."),
    plz: yup
      .string()
      .matches(/^[0-9]+$/, "Es sind nur Zahlen erlaubt")
      .required("Postleitzahl wird benötigt."),
    place: yup.string().required("Ort wird benötigt."),
    file: yup
      .mixed()
      .test("isValidUpload", "Bitte Datei hochladen.", (value) =>
        isvalidUpload(value),
      ),
    morePersons: yup.array().of(
      yup.object({
        extraName: yup.string().required("Name ist erforderlich"),
        extraSureName: yup.string().required("Nachname ist erforderlich"),
        extraBirthday: yup
          .date()
          .required("Geburtstag ist erforderlich")
          .typeError("Ungültiges Datum"),
        extraGender: yup.string().required("Geschlecht ist erforderlich"),
      }),
    ),
  }),
);

const isvalidUpload = () => {
  const upload = useRegistrationStore().studentIdentification;

  return (
    upload.length > 0 ||
    useRegistrationStore().registrationData.mainData.type !==
      RegistrationType.STUDENT
  );
};

const onFormSubmit = (values) => {
  if (values.valid) {
    router.push("/kontodaten");
  }
};

const op = ref();
const prices = ref();

const toggle = (event) => {
  op.value.toggle(event);
};

onMounted(() => {
  axios
    .get(getTargetURL() + "/priceList")
    .then((res) => {
      prices.value = res.data;
    })
    .catch((err) => {});
});

const addPersonForm = () => {
  let newPerson = {
    extraName: "",
    extraSureName: "",
    extraBirthday: "",
    extraGender: "",
  };

  useRegistrationStore().registrationData.mainData.morePersons.push(newPerson);
};

const toggleNachweisActivation = computed(() => {
  useRegistrationStore().isFilled.studentIdentification =
    !useRegistrationStore().registrationData.mainData.type ===
    "Schüler/Student über 18";
  return (
    useRegistrationStore().registrationData.mainData.type ===
    RegistrationType.STUDENT
  );
});

const isFamilyRegistration = computed(() => {
  return (
    useRegistrationStore().registrationData.mainData.type ===
    RegistrationType.FAMILY
  );
});

let courses = ref([]);
const genders = ["Männlich", "Weiblich", "Divers"];

const props = defineProps({
  isNotValid: Boolean,
});

onMounted(() => {
  axios
    .get(getTargetURL() + "/courses")
    .then((res) => {
      courses.value = res.data;
    })
    .catch((err) => {});
});
</script>

<template>
  <FormHeader header-text="Mitgliederregistrierung" />
  <Form
    v-slot="$form"
    :initialValues="useRegistrationStore().registrationData.mainData"
    id="defaultRegistrationForm"
    :resolver="resolver"
    @submit="onFormSubmit"
    class="flex flex-1 flex-col gap-[1em] max-h-[65vh] overflow-y-auto px-[2em] py-[1em]"
  >
    <Button
      variant="text"
      rounded
      size="small"
      severity="contrast"
      @click="toggle"
      icon="pi pi-question-circle"
    />
    <Popover ref="op">
      <div>
        <div v-for="price of prices">
          {{ price }}
        </div>
      </div>
    </Popover>
    <FloatLabel variant="in">
      <Select
        inputId="dd-categorie"
        name="type"
        v-model="useRegistrationStore().registrationData.mainData.type"
        :options="Object.values(RegistrationType)"
        class="w-full"
      />
      <Message
        v-if="$form.type?.invalid"
        severity="error"
        size="small"
        variant="simple"
        >{{ $form.type.error.message }}</Message
      >
      <label for="dd-categorie"> Kategorie </label>
    </FloatLabel>
    <FormField v-if="toggleNachweisActivation" name="file">
      <NachweisFeld
        :is-active="toggleNachweisActivation"
        :is-not-valid="$form.file?.invalid"
      />
    </FormField>
    <Message
      v-if="$form.file?.invalid"
      severity="error"
      size="small"
      variant="simple"
      >{{ $form.file.error.message }}</Message
    >

    <FloatLabel variant="in">
      <Select
        inputId="dd-department"
        name="reason"
        v-model="useRegistrationStore().registrationData.mainData.reason"
        :options="courses"
        class="w-full"
      />
      <Message
        v-if="$form.reason?.invalid"
        severity="error"
        size="small"
        variant="simple"
        >{{ $form.reason.error.message }}</Message
      >
      <label for="dd-department"> Abteilung </label>
    </FloatLabel>

    <div class="flex gap-[1.5em]">
      <FloatLabel variant="in" class="w-full">
        <InputText
          class="w-full"
          inputId="dd-name"
          name="name"
          v-model="useRegistrationStore().registrationData.mainData.name"
        />
        <Message
          v-if="$form.name?.invalid"
          severity="error"
          size="small"
          variant="simple"
          >{{ $form.name.error.message }}</Message
        >
        <label for="dd-name">Name</label>
      </FloatLabel>

      <FloatLabel variant="in" class="w-full">
        <InputText
          class="w-full"
          inputId="dd-secondName"
          name="surename"
          v-model="useRegistrationStore().registrationData.mainData.surename"
        />
        <label for="dd-secondName"> Nachname </label>
      </FloatLabel>
    </div>

    <div class="flex gap-[1.5em] w-full">
      <FloatLabel variant="in" class="w-full">
        <DatePicker
          class="w-full"
          name="birthday"
          v-model="useRegistrationStore().registrationData.mainData.birthday"
          dateFormat="dd.mm.yy"
          fluid
          inputId="dd-date"
          showIcon
          iconDisplay="input"
        />
        <label for="dd-date">Geburtsdatum</label>
        <Message
          v-if="$form.birthday?.invalid"
          severity="error"
          size="small"
          variant="simple"
          >{{ $form.birthday.error.message }}
        </Message>
      </FloatLabel>

      <FloatLabel variant="in" class="w-full">
        <Select
          inputId="dd-gender"
          name="gender"
          v-model="useRegistrationStore().registrationData.mainData.gender"
          :options="genders"
          class="w-full"
        />
        <Message
          v-if="$form.gender?.invalid"
          severity="error"
          size="small"
          variant="simple"
          >{{ $form.gender.error.message }}
        </Message>
        <label for="dd-gender">Geschlecht</label>
      </FloatLabel>
    </div>

    <div class="flex gap-[1.5em]">
      <FloatLabel variant="in" class="w-full">
        <InputText
          class="w-full"
          inputId="dd-email"
          name="email"
          v-model="useRegistrationStore().registrationData.mainData.email"
        />
        <label for="dd-email">Email</label>
        <Message
          v-if="$form.email?.invalid"
          severity="error"
          size="small"
          variant="simple"
          >{{ $form.email.error.message }}
        </Message>
      </FloatLabel>

      <FloatLabel variant="in" class="w-full">
        <InputText
          class="w-full"
          inputId="dd-phone"
          name="phone"
          v-model="useRegistrationStore().registrationData.mainData.phone"
        />
        <Message
          v-if="$form.phone?.invalid"
          severity="error"
          size="small"
          variant="simple"
          >{{ $form.phone.error.message }}
        </Message>
        <label for="dd-phone"> Telefon/Mobil </label>
      </FloatLabel>
    </div>

    <div class="flex gap-[1.5em]">
      <FloatLabel variant="in" class="w-full">
        <InputText
          class="w-full"
          inputId="dd-street"
          name="street"
          v-model="useRegistrationStore().registrationData.mainData.street"
        />
        <Message
          v-if="$form.street?.invalid"
          severity="error"
          size="small"
          variant="simple"
          >{{ $form.street.error.message }}
        </Message>
        <label for="dd-street">Straße</label>
      </FloatLabel>

      <FloatLabel variant="in" class="w-full">
        <InputText
          class="w-full"
          inputId="dd-plz"
          name="plz"
          v-model="useRegistrationStore().registrationData.mainData.plz"
        />
        <Message
          v-if="$form.plz?.invalid"
          severity="error"
          size="small"
          variant="simple"
          >{{ $form.plz.error.message }}</Message
        >
        <label for="dd-plz"> PLZ </label>
      </FloatLabel>
    </div>

    <div class="flex gap-[1.5em]">
      <div class="w-full">
        <FloatLabel variant="in" class="w-full">
          <InputText
            class="w-full"
            inputId="dd-Place"
            name="place"
            v-model="useRegistrationStore().registrationData.mainData.place"
          />
          <Message
            v-if="$form.place?.invalid"
            severity="error"
            size="small"
            variant="simple"
            >{{ $form.place.error.message }}
          </Message>
          <label for="dd-Place"> Ort </label>
        </FloatLabel>
      </div>

      <div class="w-full">
        <FloatLabel variant="in" class="w-full">
          <DatePicker
            class="w-full"
            name="entryDate"
            v-model="useRegistrationStore().registrationData.mainData.entryDate"
            inputId="dd-entry"
            showIcon
            fluid
            iconDisplay="input"
          />
          <label for="dd-entry">Anmeldedatum</label>
        </FloatLabel>
        <Message severity="contrast" size="small" variant="simple"
          >Wenn leer gelassen, zum nächst möglichen Zeitpunkt</Message
        >
      </div>
    </div>

    <div
      v-if="isFamilyRegistration"
      v-for="(person, index) in useRegistrationStore().registrationData.mainData
        .morePersons"
      :key="index"
    >
      <ExtraPersonForm
        v-model="
          useRegistrationStore().registrationData.mainData.morePersons[index]
        "
        :index="index"
      />
    </div>

    <AddMemberButton v-if="isFamilyRegistration" @click="addPersonForm" />
  </Form>
</template>

<style scoped></style>

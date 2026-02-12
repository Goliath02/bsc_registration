<script setup lang="ts">
import FormHeader from "@/components/FormHeader.vue";
import AddMemberButton from "@/components/BasicRegistration/AddMemberButton.vue";
import ExtraPersonForm from "@/components/BasicRegistration/ExtraPersonForm.vue";
import { MemberRegistrationStore } from "@/stores/MemberRegistrationStore.ts";
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
import { MorePersonDetails, RegistrationType } from "@/stores/Registration.ts";
import { getTargetURL } from "@/apiClient.ts";

const requiredString = (message: string) => yup.string().required(message);

const requiredDate = (message: string) =>
  yup.date().required(message).typeError("Ungültiges Datum");

// Schema für zusätzliche Personen (wiederverwendbar)
const personSchema = yup.object({
  name: requiredString("Name ist erforderlich"),
  surename: requiredString("Nachname ist erforderlich"),
  birthday: requiredDate("Geburtstag ist erforderlich"),
  gender: requiredString("Geschlecht ist erforderlich"),
});

// Hauptschema
const registrationSchema = yup.object({
  type: requiredString("Kategorie wählen."),
  reason: requiredString("Abteilung wählen."),
  name: requiredString("Name wird benötigt."),
  surename: requiredString("Nachname wird benötigt."),
  birthday: requiredDate("Geburtsdatum angeben."),
  gender: requiredString("Geschlecht angeben."),
  email: yup
    .string()
    .email("Bitte eine gültige Email angeben")
    .required("Email wird benötigt."),
  phone: requiredString("Telefonnummer wird benötigt."),
  street: requiredString("Straße wird benötigt."),
  plz: yup
    .string()
    .matches(/^\d+$/, "Es sind nur Zahlen erlaubt")
    .required("Postleitzahl wird benötigt."),
  place: requiredString("Ort wird benötigt."),
  file: yup
    .mixed()
    .test("isValidUpload", "Bitte Datei hochladen.", () => isValidUpload()),
  morePersons: yup.array().of(personSchema),
});

const basicRegistrationSchema = yupResolver(registrationSchema);

const formKey = computed(() => {
  return MemberRegistrationStore().registrationData.mainData.morePersons.length;
});

const isValidUpload = () => {
  const upload = MemberRegistrationStore().registrationData.verificationFiles;

  return (
    upload.length > 0 ||
    MemberRegistrationStore().registrationData.mainData.type !==
      RegistrationType.STUDENT
  );
};

const onFormSubmit = (values: any) => {
  if (values.valid) {
    router.push("/kontodaten");
  }
};

const op = ref();
const prices = ref();

const toggle = (event: any) => {
  op.value.toggle(event);
};

const getPriceList = () => {
  axios
    .get(getTargetURL() + "/priceList")
    .then((res) => {
      prices.value = res.data;
    })
    .catch((err) => {
      console.error("Could not fetch priceList: " + err);
    });
};

onMounted(() => {
  getPriceList();
});

const addPersonForm = () => {
  const morePerson: MorePersonDetails = {
    name: "",
    surename: "",
    gender: "",
    birthday: null,
  };

  MemberRegistrationStore().registrationData.mainData.morePersons.push(
    morePerson,
  );
};

const toggleNachweisActivation = computed(() => {
  return MemberRegistrationStore().isStudentIdentificationActive;
});

const isFamilyRegistration = computed(() => {
  return (
    MemberRegistrationStore().registrationData.mainData.type ===
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
    :key="formKey"
    v-slot="$form"
    :initialValues="MemberRegistrationStore().registrationData.mainData"
    id="defaultRegistrationForm"
    :resolver="basicRegistrationSchema"
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
        v-model="MemberRegistrationStore().registrationData.mainData.type"
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
        v-model="MemberRegistrationStore().registrationData.mainData.reason"
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
          v-model="MemberRegistrationStore().registrationData.mainData.name"
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
          v-model="MemberRegistrationStore().registrationData.mainData.surename"
        />
        <label for="dd-secondName"> Nachname </label>
      </FloatLabel>
    </div>

    <div class="flex gap-[1.5em] w-full">
      <FloatLabel variant="in" class="w-full">
        <DatePicker
          class="w-full"
          name="birthday"
          v-model="MemberRegistrationStore().registrationData.mainData.birthday"
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
          v-model="MemberRegistrationStore().registrationData.mainData.gender"
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
          v-model="MemberRegistrationStore().registrationData.mainData.email"
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
          v-model="MemberRegistrationStore().registrationData.mainData.phone"
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
          v-model="MemberRegistrationStore().registrationData.mainData.street"
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
          v-model="MemberRegistrationStore().registrationData.mainData.plz"
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
            v-model="MemberRegistrationStore().registrationData.mainData.place"
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
            v-model="
              MemberRegistrationStore().registrationData.mainData.entryDate
            "
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
      v-for="(person, index) in MemberRegistrationStore().registrationData
        .mainData.morePersons"
      :key="index"
    >
      <ExtraPersonForm
        v-model="
          MemberRegistrationStore().registrationData.mainData.morePersons[index]
        "
        :index="index"
      />
    </div>

    <AddMemberButton v-if="isFamilyRegistration" @click="addPersonForm" />
  </Form>
</template>

<style scoped></style>

<script setup lang="ts">
import { yupResolver } from "@primevue/forms/resolvers/yup";
import * as yup from "yup";
import FormHeader from "@/components/FormHeader.vue";
import IBANInput from "@/components/FinancesRegistration/IBANInput.vue";
import { Form } from "@primevue/forms";
import router from "@/router.js";
import { ref } from "vue";
import { MemberRegistrationStore } from "@/stores/MemberRegistrationStore.ts";

const financialSchema = yup.object().shape({
  iban: yup
    .string()

    .test("is-valid-iban", "Ungültige IBAN", (value: string | undefined) =>
      isValidGermanIBAN(value),
    ),
  nameOfBankOwner: yup.string().required("Name wird benötigt."),
  sureNameBankOwner: yup.string().required("Nachname wird benötigt."),
  dataProtection: yup
    .boolean()
    .oneOf([true], "Zustimmung der Datenschutzbestimmungen ist erforderlich."),
  dataCorrectness: yup
    .boolean()
    .oneOf([true], "Zustimmung der Datenschutzbestimmungen ist erforderlich."),
  dataStatute: yup
    .boolean()
    .oneOf([true], "Erkennung der Satzung des Vereins ist erforderlich."),
});

const financialRegistrationSchema = yupResolver(financialSchema);

const isValidGermanIBAN = (iban: string | undefined) => {
  iban = MemberRegistrationStore().registrationData.financialData.iban;

  if (iban === undefined) {
    return false;
  }

  // deutsche IBANs sind immer 22 Zeichen und starten mit "DE"
  const validIbanLength = iban.length === 22 && iban.startsWith("DE");

  if (!validIbanLength) {
    return false;
  }

  // Schritt 1: erste 4 Zeichen nach hinten verschieben
  const rearranged = iban.slice(4) + iban.slice(0, 4);

  // Schritt 2: alle Buchstaben in Zahlen konvertieren (A=10, ..., Z=35)
  let numeric = "";
  for (const ch of rearranged) {
    if (/[A-Z]/.test(ch)) {
      numeric += (ch.charCodeAt(0) - 55).toString();
    } else {
      numeric += ch;
    }
  }

  // Schritt 3: BigInt modulo 97 prüfen
  let validIban;
  try {
    validIban = BigInt(numeric) % 97n === 1n;
  } catch (e) {
    console.error("IBAN konnte nicht geprüft werden:", e);
    validIban = false;
  }

  return validIban && validIbanLength;
};

const ibanErrorMessage = ref("");

const onFormSubmit = (values: any) => {
  if (values.errors.iban) {
    ibanErrorMessage.value = values?.errors?.iban[0];
  }

  if (values.valid) {
    router.push("/zusammenfassung");
  }
};
</script>

<template>
  <FormHeader header-text="Mitgliederregistrierung" />

  <Form
    v-slot="$form"
    :initialValues="MemberRegistrationStore().registrationData.financialData"
    id="financialRegistrationForm"
    :resolver="financialRegistrationSchema"
    @submit="onFormSubmit"
    class="flex flex-1 flex-col gap-[1em] max-h-[65vh] overflow-y-auto px-[2em] py-[1em]"
  >
    <i-b-a-n-input
      name="iban"
      v-model="MemberRegistrationStore().registrationData.financialData.iban"
    />
    <Message v-if="$form.iban?.invalid" severity="error" variant="simple">
      {{ $form.iban.error.message }}</Message
    >

    <div class="flex gap-4">
      <FloatLabel variant="in" class="w-full">
        <InputText
          class="w-full"
          inputId="dd-accountName"
          name="nameOfBankOwner"
          v-model="
            MemberRegistrationStore().registrationData.financialData
              .nameOfBankOwner
          "
        />
        <label for="dd-accountName"> Vorname des Kontoinhabers </label>
        <Message
          v-if="$form.nameOfBankOwner?.invalid"
          severity="error"
          size="small"
          variant="simple"
        >
          {{ $form.nameOfBankOwner.error.message }}
        </Message>
      </FloatLabel>

      <FloatLabel variant="in" class="w-full">
        <InputText
          class="w-full"
          inputId="dd-accountSureName"
          name="sureNameBankOwner"
          v-model="
            MemberRegistrationStore().registrationData.financialData
              .sureNameBankOwner
          "
        />
        <label for="dd-accountSureName"> Nachname des Kontoinhabers </label>
        <Message
          v-if="$form.sureNameBankOwner?.invalid"
          severity="error"
          size="small"
          variant="simple"
        >
          {{ $form.sureNameBankOwner.error.message }}
        </Message>
      </FloatLabel>
    </div>

    <div class="flex items-center gap-4">
      <Checkbox
        inputId="dd-dataProtection"
        name="dataProtection"
        v-model="
          MemberRegistrationStore().registrationData.financialData
            .dataProtection
        "
        binary
      />
      <label for="dd-dataProtection">
        <p>
          Hiermit stimme ich den
          <router-link to="/datenschutz" class="text-blue-600 underline"
            >Datenschutzrichtlinien</router-link
          >
          zu.
        </p>
      </label>
    </div>

    <div class="flex items-center gap-4">
      <Checkbox
        inputId="dd-dataCorrectness"
        name="dataCorrectness"
        v-model="
          MemberRegistrationStore().registrationData.financialData
            .dataCorrectness
        "
        binary
      />
      <label for="dd-dataCorrectness">
        <p>
          Hiermit bestätige ich, dass alle angegebenen Daten der Richtigkeit
          ensprechen.
        </p>
      </label>
    </div>

    <div class="flex items-center gap-4">
      <Checkbox
        inputId="dd-dataStatute"
        name="dataStatute"
        v-model="
          MemberRegistrationStore().registrationData.financialData.dataStatute
        "
        binary
      />
      <label for="dd-dataStatute">
        <p>
          Hiermit erkenne ich die <a href="">Satzung</a> in ihrer aktuellen
          Fassung an und erteile die Erlaubnis zur Einziehung des
          Mitgliedsbeitrags mittels SEPA-Lastschriftmandat.
        </p>
      </label>
    </div>

    <input
      v-model="
        MemberRegistrationStore().registrationData.financialData.dataProtection
      "
      class="invisible h-0"
      type="checkbox"
    />
  </Form>
</template>

<style scoped></style>

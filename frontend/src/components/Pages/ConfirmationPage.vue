<script setup lang="ts">
import FormHeader from "@/components/FormHeader.vue";
import { formatDate } from "@/utils/dateUtil";
import { MemberRegistrationStore } from "../../stores/MemberRegistrationStore";
</script>

<template>
  <FormHeader header-text="Zusammenfassung" />
  <div
    class="flex w-full flex-col gap-[0.75em] p-[1em] max-h-[65vh] overflow-y-auto"
  >
    <h2 class="font-bold text-xl">Personen Angaben</h2>

    <div
      class="p-[1em] bg-[#141414] rounded-lg flex flex-wrap max-[1024px]:flex-col"
    >
      <div class="flex flex-row gap-[0.8em] px-[1em] py-[0.5em] w-1/2">
        <div class="font-bold">Name</div>
        <div>
          {{ MemberRegistrationStore().registrationData.mainData.name }}
        </div>
      </div>

      <div class="flex flex-row gap-[1em] px-[1em] py-[0.5em] w-1/2">
        <div class="font-bold">Nachname</div>
        <div>
          {{ MemberRegistrationStore().registrationData.mainData.surename }}
        </div>
      </div>

      <div class="flex flex-row gap-[1em] px-[1em] py-[0.5em] w-1/2">
        <div class="font-bold">Geschlecht</div>
        <div>
          {{ MemberRegistrationStore().registrationData.mainData.gender }}
        </div>
      </div>

      <div class="flex flex-row gap-[1em] px-[1em] py-[0.5em] w-1/2">
        <div class="font-bold">Geburtstag</div>
        <div>
          {{
            formatDate(
              MemberRegistrationStore().registrationData.mainData.birthday,
            )
          }}
        </div>
      </div>
    </div>

    <div
      v-for="person in MemberRegistrationStore().registrationData.mainData
        .morePersons"
      class="p-[1em] bg-[#141414] rounded-lg flex flex-wrap max-[1024px]:flex-col"
    >
      <div class="flex flex-row gap-[1em] px-[1em] w-1/2">
        <div class="font-bold">Name</div>
        <div>{{ person.name }}</div>
      </div>

      <div class="flex flex-row gap-[1em] px-[1em] w-1/2">
        <div class="font-bold">Nachname</div>
        <div>{{ person.surename }}</div>
      </div>

      <div class="flex flex-row gap-[1em] px-[1em] w-1/2">
        <div class="font-bold">Geschlecht</div>
        <div>{{ person.gender }}</div>
      </div>

      <div class="flex flex-row gap-[1em] px-[1em] w-1/2">
        <div class="font-bold">Geburtstag</div>
        <div>{{ formatDate(person.birthday) }}</div>
      </div>
    </div>

    <h2 class="font-bold text-xl">Weitere Angaben</h2>

    <div
      class="p-[1em] bg-[#141414] rounded-lg flex flex-wrap max-[1024px]:flex-col"
    >
      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Art der Anmeldung</div>
        <div>
          {{ MemberRegistrationStore().registrationData.mainData.type }}
        </div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Grund</div>
        <div>
          {{ MemberRegistrationStore().registrationData.mainData.reason }}
        </div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Straße</div>
        <div>
          {{ MemberRegistrationStore().registrationData.mainData.street }}
        </div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Email</div>
        <div>
          {{ MemberRegistrationStore().registrationData.mainData.email }}
        </div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Telefon/Mobil</div>
        <div>
          {{ MemberRegistrationStore().registrationData.mainData.phone }}
        </div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Ort</div>
        <div>
          {{ MemberRegistrationStore().registrationData.mainData.place }}
        </div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Postleitzahl</div>
        <div>{{ MemberRegistrationStore().registrationData.mainData.plz }}</div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Geburtstag</div>
        <div>
          {{
            formatDate(
              MemberRegistrationStore().registrationData.mainData.birthday,
            )
          }}
        </div>
      </div>
    </div>

    <h2 class="font-bold text-xl">Kontodaten</h2>

    <div
      class="p-[1em] bg-[#141414] rounded-lg flex flex-wrap max-[1024px]:flex-col"
    >
      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">IBAN</div>
        <div>
          {{ MemberRegistrationStore().registrationData.financialData.iban }}
        </div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Name des Kontoinhabers</div>
        <div>
          {{
            MemberRegistrationStore().registrationData.financialData
              .nameOfBankOwner
          }}
        </div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Nachname des Kontoinhabers</div>
        <div>
          {{
            MemberRegistrationStore().registrationData.financialData
              .sureNameBankOwner
          }}
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>

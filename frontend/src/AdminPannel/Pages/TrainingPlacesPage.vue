<script setup lang="ts">
import {Button} from "primevue";
import {h, ref} from "vue";
import {apiClient} from "@/apiClient";
import {useQuery} from "@tanstack/vue-query";
import PageTemplate from "@/AdminPannel/Pages/PageTemplate.vue";
import AddTrainingPlaceDialog from "@/AdminPannel/components/AddTrainingPlaceDialog.vue";

import DataTable from 'primevue/datatable';
import Column from 'primevue/column';


const isOpen = ref(false);

const getTrainingPlaces = async () => {
  const {data} = await apiClient.get("/api/places/all", {
    headers: {
      "Content-Type": "application/json",
    },
    withCredentials: true,
  });
  return data;
};

const {data, isLoading, error} = useQuery({
  queryKey: ["trainingPlace"],
  queryFn: getTrainingPlaces,
});

const places = ref(data);

const openDialog = () => {
  isOpen.value = true;
};

const buttonVNode = h(Button, {
  label: "Add Course",
  icon: "pi pi-plus",
  onClick: () => console.log("Dialog öffnen...")
});

const selectedPlace = ref();

const selectRow = (data) => {
  console.log("test", data)
};

const editingRows = ref([]);

const onRowEditSave = (event) => {
  let { newData, index } = event;

  places.value[index] = newData;
};

</script>

<template>

<PageTemplate title="Training Places" :action-button="buttonVNode">
  <AddTrainingPlaceDialog v-model="isOpen"/>

  <template #actionButton>
    <Button @click="openDialog" label="Add Place" icon="pi pi-plus" />
  </template>

  <div
      class="bg-bsc-gray overflow-y-auto rounded-lg p-4 flex flex-wrap gap-4 m-4 flex-1 items-center-safe justify-center-safe"
  >

    <div v-if="isLoading" class="w-full h-48 flex justify-center items-center" >
      <ProgressSpinner
          style="
            width: 3em;
            height: 3em;
            --p-progressspinner-color-one: #fff;
            --p-progressspinner-color-two: #fff;
            --p-progressspinner-color-three: #fff;
            --p-progressspinner-color-four: #fff;
          "
          strokeWidth="8"
          animationDuration="1.5s"
      />
    </div>
    <div v-else-if="places.length === 0" class="w-full h-48 flex justify-center items-center" >
      <h2 class="text-2xl font-bold text-[#888888]">Es gibt noch keine Orte</h2>
    </div>

    <div class="card">
      <DataTable  v-model:selection="selectedPlace" selectionMode="single" :value="places" tableStyle="min-width: 50rem" dataKey="id" editMode="row"   @row-edit-save="onRowEditSave" v-model:editingRows="editingRows">
        <Column field="id" header="ID"></Column>
        <Column field="name" header="Name">
          <template #editor="{ data, field }">
            <InputText v-model="data[field]" />
          </template>
        </Column>
        <Column field="street" header="Adresse">
          <template #editor="{ data, field }">
            <InputText v-model="data[field]" />
          </template>
        </Column>
        <Column :rowEditor="true" style="width: 10%; min-width: 8rem" bodyStyle="text-align:center"></Column>
        <Column class="w-24 !text-end">
          <template #body="{ data }">
            <Button icon="pi pi-trash" @click="selectRow(data)" severity="secondary" rounded></Button>
          </template>
        </Column>
      </DataTable>
    </div>

  </div>

</PageTemplate>

</template>

<style scoped></style>

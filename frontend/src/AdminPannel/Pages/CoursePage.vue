<script setup lang="ts">
import {Button} from 'primevue'
import CourseCard from "@/AdminPannel/components/CourseCard.vue";
import {ref} from "vue";
import AddCourseDialog from "@/AdminPannel/components/AddCourseDialog.vue";

const isOpen = ref(false);

const courses = ref([
    {
        title: "Nichtschimmer Kurs",
        imagePath: "/images/NonSwimPic.jpg",
        trainer: "Kevin Krüger",
        status: "Aktiv",
        fromDate: "01.01.2022",
        toDate: "02.01.2022",
        totalUnits: 15,
        unitsDone: 0,
        place: "Firtz-Erler Bad",
        participants: 0,
        maxParticipants: 15
    },
    {
        title: "Nichtschimmer Kurs",
        imagePath: "/images/TeenSwimPic.jpg",
        trainer: "Kevin Krüger",
        status: "Aktiv",
        fromDate: "01.01.2022",
        toDate: "02.01.2022",
        totalUnits: 15,
        unitsDone: 0,
        place: "Firtz-Erler Bad",
        participants: 0,
        maxParticipants: 15
    },
    {
        title: "Nichtschimmer Kurs",
        imagePath: "/images/MasterSwim.jpg",
        trainer: "Kevin Krüger",
        status: "Aktiv",
        fromDate: "01.01.2022",
        toDate: "02.01.2022",
        totalUnits: 15,
        unitsDone: 0,
        place: "Firtz-Erler Bad",
        participants: 0,
        maxParticipants: 15
    },
    {
        title: "Nichtschimmer Kurs",
        imagePath: "/images/TeenSwimPic.jpg",
        trainer: "Kevin Krüger",
        status: "Aktiv",
        fromDate: "01.01.2022",
        toDate: "02.01.2022",
        totalUnits: 15,
        unitsDone: 0,
        place: "Firtz-Erler Bad",
        participants: 0,
        maxParticipants: 15
    },
    {
        title: "Nichtschimmer Kurs",
        imagePath: "/images/TeenSwimPic.jpg",
        trainer: "Kevin Krüger",
        status: "Aktiv",
        fromDate: "01.01.2022",
        toDate: "02.01.2022",
        totalUnits: 15,
        unitsDone: 0,
        place: "Firtz-Erler Bad",
        participants: 0,
        maxParticipants: 15
    },
]);

const openDialog = () => {
    isOpen.value = true;
}

</script>

<template>
	<AddCourseDialog v-model="isOpen" />
	<main class="flex flex-col max-h-screen h-[92vh] md:h-[98vh]">
		<div class="flex justify-between mx-8 mt-4">
			<h2 class="text-2xl font-bold">Courses</h2>
			<Button @click="openDialog" label="Add Course" icon="pi pi-plus" />
		</div>

      <div class=" bg-bsc-gray overflow-y-auto rounded-lg p-4 flex flex-wrap gap-4 m-4 flex-1 items-start">
        <CourseCard
            v-for="course of courses"
            :key="course.title + course.fromDate"
            :title="course.title"
            :imagePath="course.imagePath"
            :trainer="course.trainer"
            :status="course.status"
            :fromDate="course.fromDate"
            :toDate="course.toDate"
            :totalUnits="course.totalUnits"
            :unitsDone="course.unitsDone"
            :place="course.place"
            :participants="course.participants"
            :maxParticipants="course.maxParticipants"
            class="w-72"
        />
      </div>
	</main>
</template>



<style scoped>

</style>
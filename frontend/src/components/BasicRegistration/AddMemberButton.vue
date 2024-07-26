<script>
export default {
  name: "AddMemberButton"
}
</script>

<template>
  <div class="flex flex-col justify-center items-center w-full p-[1em] bg-[#585858] rounded-lg cursor-pointer">
    <div class="w-fit">
      <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" fill="currentColor" class="bi bi-person-fill-add" viewBox="0 0 16 16">
        <path
            d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7m.5-5v1h1a.5.5 0 0 1 0 1h-1v1a.5.5 0 0 1-1 0v-1h-1a.5.5 0 0 1 0-1h1v-1a.5.5 0 0 1 1 0m-2-6a3 3 0 1 1-6 0 3 3 0 0 1 6 0"/>
        <path d="M2 13c0 1 1 1 1 1h5.256A4.5 4.5 0 0 1 8 12.5a4.5 4.5 0 0 1 1.544-3.393Q8.844 9.002 8 9c-5 0-6 3-6 4"/>
      </svg>
    </div>
    <p class="font-bold text-[1.2em]">Weiteres Mitglied registrieren?</p>
  </div>
</template>

<style scoped>

</style>
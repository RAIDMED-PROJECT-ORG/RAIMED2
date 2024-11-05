<template>
  <button
    class="btn m-1 flex-nowrap"
    :class="{
      'rounded-full': label === undefined,
      'w-7 h-7 min-h-5 p-0': size === 'small',
      'w-20 h-20 min-h-20': size === 'tall'
    }"
    @click="handleClick"
    :disabled="disabled"
    :style="buttonStyle"
    @mouseover="hover = true"
    @mouseleave="hover = false"
  >
    <FontAwesomeIcon
      v-if="icon"
      :icon="icon"
      :class="{'h-3 w-3': size === 'small', 'h-12 w-12': size === 'tall'}"
    />
    <span v-if="label !== undefined">{{ label }}</span>
  </button>
</template>

<script lang="ts">
import { computed, defineComponent, PropType, ref } from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { IconDefinition } from '@fortawesome/fontawesome-svg-core';

export const Color = {
  Blue: '#9ccded',
  Orange: '#f1bf6d',
  Purple: '#d0b1dd',
  Green: '#2db784',
  Grey: '#d9d9d9',
  Red: '#ff9a9b'
} as const;

type ColorType = (typeof Color)[keyof typeof Color];

export default defineComponent({
  name: 'ActionButton',
  components: { FontAwesomeIcon },
  props: {
    color: {
      type: String as PropType<ColorType>,
      required: true
    },
    size: {
      type: String as PropType<'small' | 'medium' | 'tall'>,
      default: 'medium'
    },
    icon: {
      type: Object as PropType<IconDefinition>,
      required: false
    },
    label: {
      type: String,
      required: false,
    },
    onClick: {
      type: Function as PropType<() => void>,
      required: false
    },
    disabled: {
      type: Boolean,
      default: false,
    }
  },
  setup(props) {
    const hover = ref(false);

    const buttonStyle = computed(() => {
      if (props.disabled) {
        switch (props.color) {
          case Color.Blue:
            return { backgroundColor: '#b7d8ee' };
          case Color.Orange:
            return { backgroundColor: '#eed0a1' };
          case Color.Purple:
            return { backgroundColor: '#d5c3dd' };
          case Color.Green:
            return { backgroundColor: '#8fd9b0' };
          case Color.Grey:
            return { backgroundColor: '#e6e6e6' };
          case Color.Red:
            return { backgroundColor: '#f9c0c1' };
        }
      } else if (hover.value) {
        switch (props.color) {
          case Color.Blue:
            return { backgroundColor: '#60aee2' };
          case Color.Orange:
            return { backgroundColor: '#eeb354' };
          case Color.Purple:
            return { backgroundColor: '#c694db' };
          case Color.Green:
            return { backgroundColor: '#1e8e5b' };
          case Color.Grey:
            return { backgroundColor: '#bfbfbf' };
          case Color.Red:
            return { backgroundColor: '#f26d6e' };
        }
      }
      return { backgroundColor: props.color };
    });

    return {
      hover,
      buttonStyle
    };
  },
  methods: {
    handleClick() {
      if (!this.disabled) {
        if (this.onClick) {
          this.onClick();
        }
        else {
          console.log(`Action "${this.label}" not implemented yet`);
        }
      }
    }
  }
});
</script>

<style scoped>
.btn {
  transition: background-color 0.3s ease;
}

span {
  font-weight: 600;
}
</style>

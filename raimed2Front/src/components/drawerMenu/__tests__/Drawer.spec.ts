import { describe, it, expect } from 'vitest';
import { mount, RouterLinkStub } from '@vue/test-utils';
import Drawer from '../DrawerMenu.vue';

describe('Drawer', () => {
  it('renders properly', () => {
    const wrapper = mount(Drawer, {
      global: { stubs: { RouterLink: RouterLinkStub } }
    });

    const drawerToggle = wrapper.get<HTMLInputElement>('#raimed-drawer');
    expect<string>(wrapper.text()).toContain('Projet RAIMED-2');
    expect<string>(wrapper.text()).toContain('Accueil');
    expect<string>(wrapper.text()).toContain('Patients');
    expect<string>(wrapper.text()).toContain('Vos résultats');
    expect(drawerToggle.element.checked).toBe(false);
  });
});
